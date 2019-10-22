package com.hujing.netty.basestringdemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author hujing
 * use netty impl socket programing
 */
public class MyServer {

    public static void main(String[] args) throws Exception {
        //初始化Selector和相关的事件处理线程
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {
            //初始化相关的ChannelOPtional和Attribute等
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //在BootStrap中赋值 ： EventLoopGroup , Channel , ChannelInitializer
            // 和bossGroup相关的会注册到AbstractBootStrap , 和ChildGroup相关的会注册到ServerBootStrap
            serverBootstrap.group(bossLoopGroup, workerLoopGroup).channel(NioServerSocketChannel.class).childHandler(new MyServerInitializer());
            ChannelFuture future = serverBootstrap.bind(8899).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossLoopGroup.shutdownGracefully();
            workerLoopGroup.shutdownGracefully();
        }
    }
}
