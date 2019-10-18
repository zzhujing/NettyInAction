package com.hujing.concurrent.threadpoll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<?> future = executorService.submit(() -> {
            System.out.println("Hello,ExecutorService");
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }
    }
}
