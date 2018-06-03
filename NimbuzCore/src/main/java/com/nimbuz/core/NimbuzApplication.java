/*
 * The MIT License
 *
 * Copyright 2018 Andres Tafur <atafur@facele.co>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.nimbuz.core;


import com.nimbuz.core.config.sources.EnvironmentConfigurationSource;
import com.nimbuz.core.config.sources.FileConfigurationSource;
import com.nimbuz.core.config.sources.SystemPropertyConfigurationSource;
import com.nimbuz.core.config.Configuration;
import com.nimbuz.core.config.Configuration.ConfigurationBuilder;
import com.nimbuz.core.exception.NimbuzServerException;
import com.nimbuz.core.loader.ServerModulesLoader;
import com.nimbuz.spi.NimbuzServer;
import com.nimbuz.core.util.MessagesBundle;
import java.util.logging.Logger;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
public class NimbuzApplication {

    private static boolean isStarted;
    
    private final Configuration config;
    
    private static final Logger logger = Logger.getLogger(NimbuzApplication.class.getName());

    public NimbuzApplication() throws NimbuzServerException {
        if(isStarted){
            logger.warning(MessagesBundle.getString("NIMBUZ_ALREADY_LAUNCHED"));
        }
        
        isStarted = true;
        config = ConfigurationBuilder.using(new EnvironmentConfigurationSource())
                                     .andUsing(new SystemPropertyConfigurationSource())
                                     .andUsing(new FileConfigurationSource())
                                     .build();
    }

    public void run() throws NimbuzServerException {
        String version = config.get("nimbuz.version").orElse("N/A");
        Boolean debug = config.getBoolean("nimbuz.debug").orElse(false);
        logger.info(MessagesBundle.getString("NIMBUZ_LOADING", version));
        logger.info(MessagesBundle.getString("NIMBUZ_DEBUG_MODE", debug));
        
                
        ServerModulesLoader loader = new ServerModulesLoader();
        loader.init();
        NimbuzServer server = loader.loadServletServer();
        
        logger.info("Booting the NimbuzEE runtime");
        
        server.startServer(config.getInteger("nimbuz.server.port").orElse(8080),
                                config.getBoolean("nimbuz.server.ssl").orElse(false)
                            );
        
    }


}
