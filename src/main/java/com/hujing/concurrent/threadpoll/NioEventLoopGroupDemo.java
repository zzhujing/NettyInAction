package com.hujing.concurrent.threadpoll;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;

public class NioEventLoopGroupDemo {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        EventLoop eventLoop = eventLoopGroup.next();
        Future<?> future = eventLoop.submit(() -> {
            try {
                Thread.sleep(2000L);
                throw new RuntimeException("error");
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("test for nioEventLoopGroup");
        });

        Future<String> success = eventLoop.newSucceededFuture("success");
        success.addListener(f->{
            System.out.println("success");
        });
        future.addListener(f->{
            System.out.println(f.isSuccess());
            System.out.println("terminationFuture Terminal");
        });
        System.out.println("oh yes , here are main thread");
        eventLoopGroup.shutdownGracefully();
    }
}
