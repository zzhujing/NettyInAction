package com.hujing.netty.basehttpdemo;

import com.sun.org.omg.CORBA.Initializer;
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
        /**
         * Main Reactor -> Accepter用来等待客户端的连接，然后将客户端的Channel交给Sub Reactor的io线程进行处理
         * 初始化事件线程池
         * 1.two EventLoopGroup  -> like while(true)
         **/
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //2.BootStrap
            ServerBootstrap bootstrap = new ServerBootstrap();

            /**
             * 3.add ChildHandler Initializer
             *  group() -> 初始化了AbstractBootstrap中的 Main EventLoopGroup对象和ServerBootStrap中的sub EventLoopGroup
             *  channel() -> 初始化了一个用于创建传入的 {@link io.netty.channel.Channel } 的工厂类，底层是用反射机制实现，可参考{@link io.netty.channel.ReflectiveChannelFactory}
             *  handler() -> 给Main EventLoopGroup 的Handler处理器赋值
             *  childHandler() -> 给Sub EventLoopGroup的Handler处理器赋值
             */
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new TestServerInitliazer());

            /**
             *  服务端启动的核心流程：
             *  Channel的初始化，注册到Selector
             */
            ChannelFuture channelFuture = bootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
            //4.start and shutdown
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
