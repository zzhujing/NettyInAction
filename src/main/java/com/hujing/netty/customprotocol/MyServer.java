package com.hujing.netty.customprotocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author : hujing
 * @date : 2019/10/8
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup parent = new NioEventLoopGroup();
        EventLoopGroup child = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parent, child).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new MyServerInitializer());
            ChannelFuture future = serverBootstrap.bind(8899).sync();
            future.channel().closeFuture().sync();
        } finally {
            parent.shutdownGracefully();
            child.shutdownGracefully();
        }
    }
}
