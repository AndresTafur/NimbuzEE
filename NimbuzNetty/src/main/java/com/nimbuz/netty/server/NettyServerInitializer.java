/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimbuz.netty.server;

import com.nimbuz.core.common.server.RequestHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import java.util.ServiceLoader;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        HttpServerHandler serverHandler = new HttpServerHandler();
        
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(serverHandler);
        
        ServiceLoader.load(RequestHandler.class).forEach(s -> serverHandler.addToChain(s));
    }

}
