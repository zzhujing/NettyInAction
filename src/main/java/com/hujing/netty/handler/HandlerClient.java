package com.hujing.netty.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class HandlerClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup).channel(NioSocketChannel.class).handler(new HandlerClientInitializer());
            ChannelFuture future = bootstrap.connect("localhost", 8899).sync();
            future.channel().closeFuture().sync();
        }  finally {
            clientGroup.shutdownGracefully();
        }
    }
}
