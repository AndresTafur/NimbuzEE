
package com.nimbuz.netty.server;

import com.nimbuz.core.common.server.Request;
import com.nimbuz.core.common.server.RequestHandler;
import com.nimbuz.core.common.server.Response;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

    private final Collection<RequestHandler> requestHandlers;

    private static final Logger logger = Logger.getLogger(HttpServerHandler.class.getName());
    
    public HttpServerHandler() {
        this.requestHandlers = new LinkedList<>();
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpRequest) {
            final HttpRequest req = (HttpRequest) msg;

            if (HttpUtil.is100ContinueExpected(req)) {
                ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
            }
            
            logger.log(Level.INFO, "Handling request");
            Request request = createContainerRequest(ctx, req);
            Response response = new Response();
            
            for (RequestHandler requestHandler : requestHandlers) {
                if(!requestHandler.handle(request, response) ){
                    break;
                }
            }
            
            ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
            buffer.writeBytes(response.getBody());
            
            DefaultFullHttpResponse nettyResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buffer);
            ctx.writeAndFlush(nettyResponse);
        }

        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;

            ByteBuf content = httpContent.content();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        for (RequestHandler requestHandler : requestHandlers) {
            if(!requestHandler.onException(cause) ){
                break;
            }
        }        
        
        logger.log(Level.SEVERE, "Error", cause);
        ctx.close();
    }

    private Request createContainerRequest(ChannelHandlerContext ctx, HttpRequest req) {
        Request request = new Request();
        HttpHeaders headers = req.headers();
        
        request.setUri(req.uri());
        request.setMethod(req.method().name());
        
        for (Map.Entry<String, String> header : headers) {
            request.addHeader(header.getKey(), header.getValue());
        }
        
        return request;
    }

    public void addToChain(RequestHandler s) {
        requestHandlers.add(s);
    }

}
