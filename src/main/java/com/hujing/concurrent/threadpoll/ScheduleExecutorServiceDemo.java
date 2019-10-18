package com.hujing.concurrent.threadpoll;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduleExecutorServiceDemo {
    public static void main(String[] args) throws InterruptedException {
        //使用Executors的工厂方法创建一个定时任务线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        //启动定时从调用的10毫秒之后,每10毫秒执行一次
        ScheduledFuture<?> future = scheduledExecutorService.scheduleWithFixedDelay(() -> {
                    System.out.println("beep");
                },
                10,
                10,
                TimeUnit.MILLISECONDS
        );

        //在60毫秒之后将上面的future关闭
        scheduledExecutorService.schedule(() -> {
            //取消定时任务
            future.cancel(true);
        }, 60, TimeUnit.MILLISECONDS);

        //等待60秒
        if (!scheduledExecutorService.awaitTermination(60, TimeUnit.MILLISECONDS)) {

            //直接关闭
            scheduledExecutorService.shutdownNow();
        }
    }
}
