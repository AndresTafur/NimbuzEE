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
package com.nimbuz.core.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
public class MessagesBundle {

    private static ResourceBundle messages;
    
    private static void load() {
        messages = ResourceBundle.getBundle("messages/CoreMessages");
    }

    public static String getString(String name) {
        if(messages == null){
            load();
        }
        return messages.getString(name);
    }

    public static String getString(String name, Object... args) {
        if(messages == null){
            load();
        }
        return MessageFormat.format(messages.getString(name), args);
    }
    
}
