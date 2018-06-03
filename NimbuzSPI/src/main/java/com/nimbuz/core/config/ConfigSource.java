/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimbuz.core.config;

import java.util.Optional;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
public interface ConfigSource {

    public void onInit();
    
    public Optional<String> get(String key);

    public Optional<Long> getLong(String key);

    public Optional<Boolean> getBoolean(String key);

    public Optional<Integer> getInteger(String key);
 
    public Integer getOrdinal();    

    
}
