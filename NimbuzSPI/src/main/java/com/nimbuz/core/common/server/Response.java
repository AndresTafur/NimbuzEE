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
public class Response {
    
    private final Map<String, String> headers = new HashMap<>();
    
    private byte[] body;
    

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
