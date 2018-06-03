/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimbuz.core.common;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
public enum ComponentType {
    
    //MICROPROFILE 1.0
    CDI("CDI"),
    JAX_RS("JAX-RS"),
    JSON_P("JSON-P"),
    
    //MICROPROFILE 1.1
    CONFIG("Config"),
    
    //MICROPROFILE 1.2
    FAULT_TOLERANCE("Fault Tolerance"),
    HEALTH_CHECK("Health Check"),
    METRICS("Metrics"),
    JWT("JWT"),
    
    //MICROPROFILE 1.3
    OPEN_API("OpenAPI"),
    OPEN_TRACING("OpenTracing"),
    REST_CLIENT("RestClient"),
    
    //OTHERS
    SERVLET("Servlet"),
    WEBSOCKET("WebSocket"),
    EL("EL"),
    JPA("JPA"),
    JAX_WS("JAX-WS"),
    BEAN_VALIDATION("Bean Validation"),
    JSON_B("JSON-B"),
    JTA("JTA"),
    EJB("EJB"),
    BATCH("Batch"),
    MAIL("JavaMail"),
    ;

    private final String name;

    ComponentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }    
    
}
