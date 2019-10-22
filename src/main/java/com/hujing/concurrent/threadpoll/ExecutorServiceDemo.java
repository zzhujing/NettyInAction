package com.hujing.concurrent.threadpoll;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //创建含有两个线程的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        //初始化两个任务
        Callable<String> spendFiveSecondTask = ()->{
            try {
                TimeUnit.SECONDS.sleep(5);
                return "five";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        };
        Callable<String> spendTwoSecondTask = ()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                return "two";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        };

//        testInvokeAll(executorService,spendFiveSecondTask,spendTwoSecondTask);
        testSubmit(executorService, spendFiveSecondTask, spendTwoSecondTask);
    }


    @SafeVarargs
    private static void testSubmit(ExecutorService executorService, Callable<String>... callable) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        Future<String> f1 = executorService.submit(callable[0]);
        Future<String> f2 = executorService.submit(callable[1]);
        for (; ; ) {
            if (f1.isDone() && f2.isDone()) {
                System.out.println(f1.get());
                System.out.println(f2.get());
                break;
            }
        }
        System.out.printf("spend time : %s s",(System.currentTimeMillis()-start)/1000);
        executorService.shutdown();
    }

    private static void testInvokeAll(ExecutorService executorService, Callable<String> spendFiveSecondTask, Callable<String> spendTwoSecondTask) throws InterruptedException {

        List<Callable<String>> tasks = Arrays.asList(spendFiveSecondTask, spendTwoSecondTask);

        long start = System.currentTimeMillis();

        //执行Tasks任务列表
        List<Future<String>> futures = executorService.invokeAll(tasks);
        futures.forEach(f->{
            if (f.isDone()) {
                try {
                    System.out.println(f.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.printf("spend time : %s  s",(System.currentTimeMillis()-start) / 1000);
        //关闭线程池
        executorService.shutdown();
    }
}
