package com.hujing.netty.basehttpdemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author hujing
 * first netty http server
 */
public class TestServer {

    public static void main(String[] args) throws Exception {
        //1.two EventLoopGroup  -> like while(true)
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //2.BootStrap
            ServerBootstrap bootstrap = new ServerBootstrap();
<<<<<<< HEAD
            //3.add ChildHandler Initializer
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new TestServerInitliazer());
=======
            //3.add ChildHandler Initliazer
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new TestServerInitliazer());

            //初始化Channel并且
>>>>>>> 077df6a0d82ab93052e54d386e6364b45d12ee42
            ChannelFuture channelFuture = bootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
            //4.start and shutdown
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
