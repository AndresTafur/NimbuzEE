
package com.nimbuz.netty.server;

import com.nimbuz.core.common.server.NimbuzServerDef;
import com.nimbuz.core.common.server.RequestHandler;
import com.nimbuz.spi.NimbuzServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andres Tafur <atafur@facele.co>
 */
@NimbuzServerDef("NimbuzNettyServer")
public class NimbuzNettyServer implements NimbuzServer {

    private final EventLoopGroup bossGroup;
    
    private final EventLoopGroup workerGroup;
    
    private final ServerBootstrap nettyServer;



    public NimbuzNettyServer() {
        
        // Configure the server.
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        nettyServer = new ServerBootstrap();
        nettyServer.option(ChannelOption.SO_BACKLOG, 1024);
        nettyServer.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
//                .handler(new LoggingHandler())
                .childHandler(new NettyServerInitializer());
    }

    @Override
    public void startServer(int port, boolean useSSL) {
        
        Logger.getLogger(NimbuzNettyServer.class.getName()).info("Starting servlet server...");
        
        try {
            Channel ch = nettyServer.bind(port).sync().channel();
            
            Logger.getLogger(NimbuzNettyServer.class.getName()).info("READY!!");
            ch.closeFuture().syncUninterruptibly();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(NimbuzNettyServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void stopServer() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
