/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimbuz.sample.nimbuzsample;

import com.nimbuz.core.common.server.Request;
import com.nimbuz.core.common.server.RequestHandler;
import com.nimbuz.core.common.server.Response;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */

public class ServletSample implements RequestHandler {

    @Override
    public boolean onException(Throwable cause) {
        return true;
    }

    @Override
    public boolean handle(Request request, Response response) {
        System.out.println(""+request.getUri());
        response.setBody("Hello".getBytes());
        return true;
    }
    
}
