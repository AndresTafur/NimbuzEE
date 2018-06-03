
package com.nimbuz.core.config.sources;

import com.nimbuz.core.config.ConfigSource;
import java.util.Optional;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
public class EnvironmentConfigurationSource implements ConfigSource {

    public EnvironmentConfigurationSource() {
    }

    @Override
    public void onInit() {
    }    
    
    @Override
    public Optional<String> get(String key) {
        String value = System.getenv(parseKeyNameForEnvironmentVariables(key));

        if (value == null) {
            value = System.getenv(parseKeyNameForEnvironmentVariablesLegacy(key));
        }

        if (value == null) {
            value = System.getenv(key);
        }

        return (value == null) ? Optional.empty() : Optional.of(value);
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
        return 300;
    }

    private String parseKeyNameForEnvironmentVariables(String key) {
        return key.toUpperCase().replaceAll("\\[", "").replaceAll("]", "")
                .replaceAll("-", "").replaceAll("\\.", "_");
    }

    private String parseKeyNameForEnvironmentVariablesLegacy(String key) {
        return key.toUpperCase().replaceAll("\\.", "_");
    }



    
}
