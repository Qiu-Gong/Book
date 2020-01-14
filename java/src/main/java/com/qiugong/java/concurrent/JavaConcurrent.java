package com.qiugong.java.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author qzx 20/1/14.
 */
public class JavaConcurrent {

    public static void main(String[] args) {

        {
            // Runnable
            MyRunnable runnable = new MyRunnable();
            Thread t1 = new Thread(runnable);
            t1.start();
        }

        {
            // Callback
            long time = System.currentTimeMillis();
            MyCallback callback = new MyCallback();
            FutureTask<Integer> ft = new FutureTask<>(callback);
            Thread t2 = new Thread(ft);
            t2.start();
            try {
                int value = ft.get();
                System.out.println("time:" + (System.currentTimeMillis() - time) + " value:" + value);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        {
            // Thread
            MyThread mt = new MyThread();
            mt.start();
        }

        {
            // Executor
            ExecutorService executorService = Executors.newCachedThreadPool();
            for (int i = 0; i < 5; i++) {
                executorService.execute(new MyRunnable());
            }
            executorService.shutdown();
        }

        {
            // InterruptedException
            Thread t3 = new MyThread1();
            t3.start();
            t3.interrupt();
        }

        {
            try {
                Thread t4 = new MyThread2();
                t4.start();
                Thread.sleep(1);
                t4.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        {
            ExecutorService executorService = Executors.newCachedThreadPool();
            Future<Integer> future = executorService.submit(() -> 123);
            future.cancel(true);
        }
    }

    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("MyRunnable");
        }
    }

    public static class MyCallback implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Thread.sleep(1000);
            return 123;
        }
    }

    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread");
        }
    }

    public static class MyThread1 extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                System.out.println("thread run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class MyThread2 extends Thread {
        @Override
        public void run() {
            while (!interrupted()) {
                System.out.println("Thread run....");
            }
            System.out.println("Thread end");
        }
    }
}
