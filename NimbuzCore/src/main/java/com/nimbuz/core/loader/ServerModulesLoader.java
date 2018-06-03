/*
 *  Copyright (c) 2014-2017 Kumuluz and/or its affiliates
 *  and other contributors as indicated by the @author tags and
 *  the contributor list.
 *
 *  Licensed under the MIT License (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  https://opensource.org/licenses/MIT
 *
 *  The software is provided "AS IS", WITHOUT WARRANTY OF ANY KIND, express or
 *  implied, including but not limited to the warranties of merchantability,
 *  fitness for a particular purpose and noninfringement. in no event shall the
 *  authors or copyright holders be liable for any claim, damages or other
 *  liability, whether in an action of contract, tort or otherwise, arising from,
 *  out of or in connection with the software or the use or other dealings in the
 *  software. See the License for the specific language governing permissions and
 *  limitations under the License.
*/
package com.nimbuz.core.loader;


import com.nimbuz.core.common.ComponentType;
import com.nimbuz.spi.NimbuzComponent;
import com.nimbuz.core.common.NimbuzComponentProvider;
import com.nimbuz.spi.NimbuzServer;
import com.nimbuz.core.exception.NimbuzServerException;
import com.nimbuz.core.util.MessagesBundle;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Logger;
import com.nimbuz.core.common.server.NimbuzServerDef;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andres Tafur
 * 
 */
public class ServerModulesLoader {

    private static final Logger logger = Logger.getLogger(ServerModulesLoader.class.getSimpleName());
    
    
    private Map<ComponentType,NimbuzModule> components;
    private Map<String,NimbuzModule> namedcomponents;
    
    
    public void init() {
        logger.finest(MessagesBundle.getString("NIMBUZ_SERVER_SCANNING"));
        components = new EnumMap<>(ComponentType.class);
        namedcomponents = new HashMap<>();
        ServiceLoader.load(NimbuzComponent.class).forEach(this::processComponents);
    }
    

    public NimbuzServer loadServletServer() throws NimbuzServerException {
        List<NimbuzServer> servers = new LinkedList<>();
        logger.info(MessagesBundle.getString("NIMBUZ_SERVER_LOADING"));
        
        ServiceLoader.load(NimbuzServer.class).forEach(s -> processServer(s, servers));

        if (servers.isEmpty()) {
            logger.severe(MessagesBundle.getString("NIMBUZ_SERVER_NOT_FOUND"));
            throw new NimbuzServerException(MessagesBundle.getString("NIMBUZ_SERVER_NOT_FOUND"));
        }

        if (servers.size() > 1) {
            logger.severe(MessagesBundle.getString("NIMBUZ_SERVER_TOO_MANY"));
            throw new NimbuzServerException(MessagesBundle.getString("NIMBUZ_SERVER_TOO_MANY"));
        }

        NimbuzServer server = servers.get(0);
        return server;
    }
    
    private void processServer(NimbuzServer server, List<NimbuzServer> servers) throws NimbuzServerException {
        NimbuzServerDef serverAnnotation = server.getClass().getDeclaredAnnotation(NimbuzServerDef.class);

        if (serverAnnotation == null) {
            String msg =  MessagesBundle.getString("NIMBUZ_SERVER_MISSING_ANNOTATION", server.getClass().getSimpleName() );
            logger.severe(msg);
            throw new NimbuzServerException(msg);
        }
        servers.add(server);
        logger.info(MessagesBundle.getString("NIMBUZ_SERVER_FOUND", serverAnnotation.value()));        
    }
    
    
    private void processComponents(NimbuzComponent component) throws NimbuzServerException {
        NimbuzModule module = new NimbuzModule(component);
        
        for (ComponentType type : module.getComponents()) {
            if(components.containsKey(type)){
                throw new NimbuzServerException(MessagesBundle.getString("NIMBUZ_COMPONENT_TOO_MANY", type, Arrays.asList(components.get(type), component)));
            }
            components.put(type, module);
        }
        namedcomponents.put(module.getName(), module);
        
        for (NimbuzModule value : components.values()) {
            for (String dependency : value.getDependencies()) {
                if(!namedcomponents.containsKey(dependency)){
                    throw new NimbuzServerException(MessagesBundle.getString("NIMBUZ_COMPONENT_MISSING_DEP", value.getName(), dependency));
                }
            }
        }
        
        logger.info(MessagesBundle.getString("NIMBUZ_COMPONENT_FOUND", module.getName(), module.getComponents()));
    }    

    private static class NimbuzModule{

        private List<ComponentType> components;

        private List<String> dependencies;

        private NimbuzComponent component;

        private String name;
        
        private NimbuzModule(NimbuzComponent component) {
            NimbuzComponentProvider serverAnnotation = component.getClass().getDeclaredAnnotation(NimbuzComponentProvider.class);

            if (serverAnnotation == null) {
                String msg =  MessagesBundle.getString("NIMBUZ_COMPONENT_MISSING_ANNOTATION", component.getClass().getSimpleName() );
                logger.severe(msg);
                throw new NimbuzServerException(msg);
            }
            name = serverAnnotation.value();
            components = Arrays.asList(serverAnnotation.components());
            dependencies = Arrays.asList(serverAnnotation.dependencies());
            this.component = component;
        }

        public List<ComponentType> getComponents() {
            return components;
        }

        public List<String> getDependencies() {
            return dependencies;
        }

        public NimbuzComponent getComponent() {
            return component;
        }

        public String getName() {
            return name;
        }
        
    }

}
