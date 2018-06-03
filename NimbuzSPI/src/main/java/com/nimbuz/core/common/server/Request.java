/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimbuz.core.common.server;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
public class Request {

    private String method;
    
    private final Map<String, String> headers = new HashMap<>();
    
    private String uri;
    

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
    
}
