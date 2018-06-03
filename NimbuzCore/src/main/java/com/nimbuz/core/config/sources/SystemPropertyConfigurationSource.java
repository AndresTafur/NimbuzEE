
package com.nimbuz.core.config.sources;

import com.nimbuz.core.config.ConfigSource;
import java.util.Optional;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
public class SystemPropertyConfigurationSource implements ConfigSource {

    public SystemPropertyConfigurationSource() {
    }


    @Override
    public void onInit() {
    }    
    
    @Override
    public Optional<String> get(String key) {
        return Optional.ofNullable(System.getProperty(key));
    }

    @Override
    public Optional<Long> getLong(String key) {
        try{
           return get(key).map(v -> Long.parseLong(v));
        }catch(NumberFormatException ex){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> getBoolean(String key) {
        try{
           return get(key).map(v -> Boolean.parseBoolean(v));
        }catch(NumberFormatException ex){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> getInteger(String key) {
           return get(key).map(v -> Integer.parseInt(v));
    }    

    @Override
    public Integer getOrdinal() {
        return 400;
    }


    
}
