package com.hujing.concurrent.threadpoll;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduleExecutorServiceDemo {
    public static void main(String[] args) throws InterruptedException {
//       testScheduled1();
        testScheduled2();
    }


    /**
     * {@link ScheduledExecutorService#scheduleAtFixedRate(Runnable, long, long, TimeUnit)}
     * 和
     * {@link ScheduledExecutorService#scheduleWithFixedDelay(Runnable, long, long, TimeUnit)}
     * 的区别
     */
    private static void testScheduled2() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

        ScheduledFuture<?> future = scheduledExecutorService.scheduleAtFixedRate(
                () -> {
                    try {
                        Thread.sleep(1000L);
                        System.out.println("executor scheduledWithFixedDelay");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                1000,
                1000,
                TimeUnit.MILLISECONDS
        );

        System.out.println(future.getDelay(TimeUnit.MILLISECONDS));

        scheduledExecutorService.schedule(
                ()->{
                    future.cancel(false);
                },
                7000,
                TimeUnit.MILLISECONDS
        );

        if (!scheduledExecutorService.awaitTermination(8000, TimeUnit.MILLISECONDS)) {
            scheduledExecutorService.shutdown();
        }
    }


    /**
     *  测试一个定时任务，没10毫秒执行一次，60毫秒之后取消定时任务
     * @throws InterruptedException
     */
    private static void testScheduled1() throws InterruptedException {

        //使用Executors的工厂方法创建一个定时任务线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

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
//            直接关闭
            scheduledExecutorService.shutdown();
        }
    }
}
