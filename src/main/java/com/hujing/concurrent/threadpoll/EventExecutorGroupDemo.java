package com.hujing.concurrent.threadpoll;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class EventExecutorGroupDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(2);
        //测试便利Group中所有的EventExecutor执行Task
//        testIterator(eventExecutorGroup);
        testNextAndTerminalFuture(eventExecutorGroup);
        eventExecutorGroup.shutdownGracefully();
    }

    /**
     * 测试next()方法和terminalFuture的一般用法
     * @param eventExecutorGroup
     */
    private static void testNextAndTerminalFuture(EventExecutorGroup eventExecutorGroup) {
        for (int i = 0; i < 10; i++) {
            eventExecutorGroup.next().submit(()->{
                System.out.println(Thread.currentThread().getName());
                System.out.println("test next()");
            });
        }
        Future<?> future = eventExecutorGroup.terminationFuture();
        //给terminalFuture中添加一个Listener
        future.addListener(f->{
            System.out.println("terminal..");
        });
    }

    private static void testIterator(EventExecutorGroup eventExecutorGroup) throws InterruptedException, ExecutionException {
        Iterator<EventExecutor> iterator = eventExecutorGroup.iterator();
        while (iterator.hasNext()) {
            Future<?> future = iterator.next().submit(() -> {
                System.out.println("Hello,EventExecutorGroup");
            });
            if (future.isSuccess()) {
                System.out.println(future.get());
            }
        }
    }
}
