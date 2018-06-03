/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimbuz.core.common.server;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
public interface RequestHandler {

    public boolean onException(Throwable cause);

    public boolean handle(Request request, Response response);
    
}
