
package com.nimbuz.core.config;

import com.nimbuz.core.exception.NimbuzServerException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Function;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
public class Configuration {

    private final Collection<ConfigSource> configSources;

    public Configuration() {
        configSources = new LinkedList<>();
    }
    
    public void validate() throws NimbuzServerException {
    }

    void initialize() throws NimbuzServerException {
        configSources.forEach(ConfigSource::onInit); 
    }
    
    public Optional<String> get(String key) {
        return get(s -> s.get(key));
    }

    public Optional<Boolean> getBoolean(String key) {
        return get(s -> s.getBoolean(key));    
    }

    public Optional<Integer> getInteger(String key) {
         return get(s -> s.getInteger(key));    
    }

    public Optional<Long> getLong(String key) {
         return get(s -> s.getLong(key));    
    }    
    
    private <T> Optional<T> get(Function<ConfigSource, Optional<T>> func ) {
         for (ConfigSource configurationSource : configSources) {
             Optional<T> value = func.apply(configurationSource);
            if (value.isPresent()) {
                return value;
            }
        }
        return Optional.empty();
    }        
    
    public static class ConfigurationBuilder {

        private final Configuration config;

         public static ConfigurationBuilder using(ConfigSource configSource) throws NimbuzServerException {
             return new ConfigurationBuilder(configSource);
         }

        public static Optional<Configuration> loadConfig() {
            return Optional.of(new Configuration());
        }

        private ConfigurationBuilder(ConfigSource configSource) {
            config = new Configuration();
            config.configSources.add(configSource);
        }

        public ConfigurationBuilder andUsing(ConfigSource configSource) {
            config.configSources.add(configSource);
            return this;
        }

        public Configuration build() throws NimbuzServerException {
            config.validate();
            config.initialize();
            return config;
        }

    }    
    
}
