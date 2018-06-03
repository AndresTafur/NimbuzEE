/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimbuz.spi;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
public interface NimbuzServer {
    
    public void startServer(int port, boolean useSSL);

    public void stopServer();
    
}
