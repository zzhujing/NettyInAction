package com.hujing.concurrent.threadpoll;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;

public class EventExecutorDemo {
    public static void main(String[] args) {
        EventExecutorGroup group = new NioEventLoopGroup();
        EventExecutor next = group.next();
        next.submit(() -> {
            if (next.inEventLoop()) {
                System.out.println("hahaha");
            }
        });
        group.shutdownGracefully();
    }
}
