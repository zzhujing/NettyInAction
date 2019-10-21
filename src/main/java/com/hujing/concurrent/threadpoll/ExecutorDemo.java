package com.hujing.concurrent.threadpoll;

import java.util.concurrent.Executor;

public class ExecutorDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main Thread Begin");
        new ThreadPreExecutor().execute(()->{
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Main Thread End");
    }

    static class ThreadPreExecutor implements Executor{
        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }
}
