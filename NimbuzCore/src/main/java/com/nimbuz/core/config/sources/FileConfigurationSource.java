
package com.nimbuz.core.config.sources;

import com.nimbuz.core.config.ConfigSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
public class FileConfigurationSource implements ConfigSource {

    private String ymlFileName;
    private String yamlFileName;
    private String propertiesFileName;
    private final String microProfilePropertiesFileName;
    private Map<String, Object> config;
    private Properties properties;

    public FileConfigurationSource() {

        this.ymlFileName = "config.yml";
        this.yamlFileName = "config.yaml";
        this.microProfilePropertiesFileName = "META-INF/microprofile-config.properties";

        String configurationFileName = System.getProperty("com.nimbuz.ee.configuration.file");

        if (configurationFileName != null && !configurationFileName.isEmpty()) {
            this.ymlFileName = configurationFileName;
            this.yamlFileName = configurationFileName;
            this.propertiesFileName = configurationFileName;
        }
    }


    @Override
    public void onInit() {
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
    

    public void init() {
        // read yaml file to Map<String, Object>
        InputStream file;
        Yaml yaml = new Yaml();
        try {
            file = getClass().getClassLoader().getResourceAsStream(ymlFileName);

            if (file == null) {
                file = getClass().getClassLoader().getResourceAsStream(yamlFileName);
            }

            if (file == null) {
                try {
                    file = Files.newInputStream(Paths.get(ymlFileName));
                } catch (IOException ignored) {
                }
            }

            if (file == null) {
                try {
                    file = Files.newInputStream(Paths.get(yamlFileName));
                } catch (IOException ignored) {
                }
            }

            if (file != null) {

                Object yamlParsed = yaml.load(file);

                if (yamlParsed instanceof Map) {
                    config = (Map<String, Object>) yamlParsed;
                } else {

                }

                file.close();
            }
        } catch (IOException e) {

        }

        // parse properties file
        if (config == null) {
            loadProperties(propertiesFileName);
            if (properties == null) {
                loadProperties(microProfilePropertiesFileName);
            }
        }

    }

    @Override
    public Optional<String> get(String key) {

        // get key value from yaml configuration
        if (config != null) {

            Object value = getValue(key);

            return (value == null) ? Optional.empty() : Optional.of(value.toString());

            // get value from .properties configuration
        } else if (properties != null) {

            String value = properties.getProperty(key);
            if (value != null) {
                return Optional.of(value);
            }
        }

        return Optional.empty();
    }


    @Override
    public Integer getOrdinal() {
        return 100;
    }

    /**
     * Returns true, if key represents an array.
     *
     * @param key configuration key
     * @return true if the config key represents an array, false otherwise.
     */
    private boolean representsArray(String key) {
        int openingBracket = key.indexOf("[");
        int closingBracket = key.indexOf("]");
        return closingBracket == key.length() - 1 && openingBracket != -1;
    }

    /**
     * Parses configuration map, returns value for given key.
     *
     * @param key configuration key
     * @return Value for given key.
     */
    private Object getValue(String key) {

        // iterate over configuration tree
        String[] splittedKeys = key.split("\\.");
        Object value = config;

        for (int i = 0; i < splittedKeys.length; i++) {

            String splittedKey = splittedKeys[i];

            if (value == null) {
                return null;
            }

            // parse arrays
            if (representsArray(splittedKey)) {

                // value array support
                int arrayIndex;
                int openingBracket = splittedKey.indexOf("[");
                int closingBracket = splittedKey.indexOf("]");

                try {
                    arrayIndex = Integer.parseInt(splittedKey.substring(openingBracket + 1, closingBracket));
                } catch (NumberFormatException e) {
                    return null;
                }

                splittedKey = splittedKey.substring(0, openingBracket);

                if (value instanceof Map) {
                    value = ((Map) value).get(splittedKey);
                } else {
                    return null;
                }

                if (value instanceof List) {
                    value = (arrayIndex < ((List) value).size()) ? ((List) value).get(arrayIndex) : null;
                }

            } else {
                if (value instanceof Map) {

                    Object tmpValue = ((Map) value).get(splittedKey);

                    if (tmpValue == null && i != splittedKeys.length - 1) {

                        String postfixKey = Arrays.stream(splittedKeys).skip(i)
                                .collect(Collectors.joining("."));

                        return ((Map) value).get(postfixKey);
                    } else {

                        value = tmpValue;
                    }
                } else {
                    return null;
                }
            }
        }

        return value;
    }

    private void loadProperties(String fileName) {

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

            if (inputStream == null) {
                try {
                    inputStream = Files.newInputStream(Paths.get(fileName));
                } catch (IOException ignored) {
                }
            }

            if (inputStream != null) {
                properties = new Properties();
                properties.load(inputStream);

                inputStream.close();
            }
        } catch (IOException e) {
        }
    }

    
}
