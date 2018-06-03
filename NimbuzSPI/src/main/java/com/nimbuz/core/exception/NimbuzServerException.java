/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimbuz.core.exception;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
public class NimbuzServerException extends RuntimeException {

    public NimbuzServerException(String message) {
        super(message);
    }
    
}
