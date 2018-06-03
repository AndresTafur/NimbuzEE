/*
 * The MIT License
 *
 * Copyright 2018 Andres Tafur <atafur@facele.co>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.nimbuz.mock;


import com.nimbuz.core.common.server.NimbuzServerDef;
import com.nimbuz.spi.NimbuzServer;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
@NimbuzServerDef("NimbuzMockServer")
public class NimbuzMockServer implements NimbuzServer {

    private static final Logger logger = Logger.getLogger(NimbuzMockServer.class.getName());



    @Override
    public void startServer(int port, boolean useSSL) {
        logger.info("Starting MockNimbuz server");
        logger.log(Level.INFO, "Using port: {0}", port);       
        logger.log(Level.INFO, "enable ssl: {0}", useSSL);       
    }

    @Override
    public void stopServer() {
       logger.log(Level.INFO, "Stopping MockNimbuz server");
    }

    
}
