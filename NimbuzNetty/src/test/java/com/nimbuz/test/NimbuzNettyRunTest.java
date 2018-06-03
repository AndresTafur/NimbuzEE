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
package com.nimbuz.test;


import com.nimbuz.core.exception.NimbuzServerException;
import com.nimbuz.netty.server.NimbuzNettyServer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
public class NimbuzNettyRunTest {
    
    @Test
    public void testRun() throws NimbuzServerException {
        try {
            NimbuzNettyServer app = new NimbuzNettyServer();
            
            ExecutorService executor = Executors.newSingleThreadExecutor();
            
            executor.submit(() -> { app.startServer(8080, false); });

            Thread.sleep(10000);
            
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(NimbuzNettyRunTest.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
    }
    
}
