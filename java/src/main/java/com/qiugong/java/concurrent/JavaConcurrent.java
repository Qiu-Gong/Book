package com.qiugong.java.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
            Thread t4 = new MyThread2();
            t4.start();
            t4.interrupt();
        }

        {
            ExecutorService executorService = Executors.newCachedThreadPool();
            Future<Integer> future = executorService.submit(() -> 123);
            future.cancel(true);
            executorService.shutdown();
        }

        {
            final SynchronizedExample e1 = new SynchronizedExample();
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(e1::func1);
            executorService.execute(e1::func1);
            executorService.shutdown();
        }

        {
            final SynchronizedExample e1 = new SynchronizedExample();
            final SynchronizedExample e2 = new SynchronizedExample();
            ExecutorService executorService = Executors.newCachedThreadPool();
            System.out.println();
            System.out.println("------1-----");
            executorService.execute(e1::func1);
            executorService.execute(e2::func1);
            executorService.shutdown();
        }

        {
            final SynchronizedExample2 e1 = new SynchronizedExample2();
            final SynchronizedExample2 e2 = new SynchronizedExample2();
            ExecutorService executorService = Executors.newCachedThreadPool();
            System.out.println();
            System.out.println("------2-----");
            executorService.execute(e1::func1);
            executorService.execute(e2::func1);
            executorService.shutdown();
        }

        {
            final LockExample lockExample = new LockExample();
            ExecutorService executorService = Executors.newCachedThreadPool();
            System.out.println();
            System.out.println("------3-----");
            executorService.execute(lockExample::func);
            executorService.execute(lockExample::func);
            executorService.shutdown();
        }

        {
            JoinExample example = new JoinExample();
            System.out.println();
            example.test();
        }

        {
            ExecutorService executorService = Executors.newCachedThreadPool();
            waitNotifyExample example = new waitNotifyExample();
            executorService.execute(example::after);
            executorService.execute(example::before);
            executorService.shutdown();
        }

        {
            ExecutorService executorService = Executors.newCachedThreadPool();
            AwaitSignalExample example = new AwaitSignalExample();
            executorService.execute(example::after);
            executorService.execute(example::before);
            executorService.shutdown();
        }

        {
            final int totalThread = 10;
            CountDownLatch countDownLatch = new CountDownLatch(totalThread);
            ExecutorService executorService = Executors.newCachedThreadPool();
            System.out.println("start");
            for (int i = 0; i < totalThread; i++) {
                executorService.execute(() -> {
                    System.out.print("run..");
                    countDownLatch.countDown();
                });
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end");
            executorService.shutdown();
        }

        {
            final int totalThread = 10;
            CyclicBarrier cyclicBarrier = new CyclicBarrier(totalThread, () -> System.out.println("run"));
            ExecutorService executorService = Executors.newCachedThreadPool();
            for (int i = 0; i < totalThread; i++) {
                int finalI = i;
                executorService.execute(() -> {
                    System.out.print("before.." + finalI + " ");
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.print("after.." + finalI + " ");
                });
            }
            executorService.shutdown();
        }

        {
            final int clientCount = 3;
            final int totalRequestCount = 10;
            Semaphore semaphore = new Semaphore(clientCount);
            ExecutorService executorService = Executors.newCachedThreadPool();
            for (int i = 0; i < totalRequestCount; i++) {
                executorService.execute(() -> {
                    try {
                        semaphore.acquire();
                        System.out.print(semaphore.availablePermits() + " ");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release();
                    }
                });
            }
            executorService.shutdown();
        }

        {
            FutureTask<Integer> futureTask = new FutureTask<>(() -> {
                int result = 0;
                for (int i = 0; i < 100; i++) {
                    Thread.sleep(10);
                    result += i;
                }
                return result;
            });
            Thread computeThread = new Thread(futureTask);
            computeThread.start();

            Thread otherThread = new Thread(() -> {
                System.out.println("other task is running...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            otherThread.start();

            try {
                System.out.println(futureTask.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        {
            long time = System.currentTimeMillis();
            ForkJoinExample example = new ForkJoinExample(1, 1000000);
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            Future result = forkJoinPool.submit(example);
            try {
                System.out.println(result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println("Fork Join time:" + (System.currentTimeMillis() - time));
        }

        {
            final int threadSize = 1000;
            ThreadUnsafeExample example = new ThreadUnsafeExample();
            final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
            ExecutorService executorService = Executors.newCachedThreadPool();
            for (int i = 0; i < threadSize; i++) {
                executorService.execute(example::add);
                countDownLatch.countDown();
            }
            try {
                countDownLatch.await();
                executorService.shutdown();
                System.out.println(example.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        {
            final int threadSize = 1000;
            ThreadsafeExample example = new ThreadsafeExample();
            final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
            ExecutorService executorService = Executors.newCachedThreadPool();
            for (int i = 0; i < threadSize; i++) {
                executorService.execute(example::add);
                countDownLatch.countDown();
            }
            try {
                countDownLatch.await();
                executorService.shutdown();
                System.out.println(example.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    public static class SynchronizedExample {
        public void func1() {
            synchronized (this) {
                for (int i = 0; i < 10; i++) {
                    System.out.print(i + " ");
                }
            }
        }
    }

    public static class SynchronizedExample2 {
        public void func1() {
            synchronized (SynchronizedExample2.class) {
                for (int i = 0; i < 10; i++) {
                    System.out.print(i + " ");
                }
            }
        }
    }

    public static class LockExample {
        private Lock lock = new ReentrantLock();

        public void func() {
            lock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.print(i + " ");
                }
            } finally {
                lock.unlock(); // 确保释放锁，从而避免发生死锁。
            }
        }
    }

    public static class JoinExample {
        private class A extends Thread {
            @Override
            public void run() {
                System.out.println("A");
            }
        }

        private class B extends Thread {
            private A a;

            B(A a) {
                this.a = a;
            }

            @Override
            public void run() {
                try {
                    a.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B");
            }
        }

        public void test() {
            A a = new A();
            B b = new B(a);
            b.start();
            a.start();
        }
    }

    public static class waitNotifyExample {
        public synchronized void before() {
            System.out.println("before");
            notifyAll();
        }

        public synchronized void after() {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("after");
        }
    }

    public static class AwaitSignalExample {
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        public void before() {
            lock.lock();
            try {
                System.out.println("before");
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public void after() {
            lock.lock();
            try {
                condition.await();
                System.out.println("after");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static class ProducerConsumer {
        private static BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

        private static class Producer extends Thread {
            @Override
            public void run() {
                try {
                    queue.put("product");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("produce..");
            }
        }

        private static class Consumer extends Thread {
            @Override
            public void run() {
                try {
                    String product = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("consume..");
            }
        }
    }

    public static class ForkJoinExample extends RecursiveTask<Integer> {
        private final int threshold = 5;
        private int first;
        private int last;

        public ForkJoinExample(int first, int last) {
            this.first = first;
            this.last = last;
        }

        @Override
        protected Integer compute() {
            int result = 0;
            if (last - first <= threshold) {
                // 任务足够小则直接计算
                for (int i = first; i <= last; i++) {
                    result += i;
                }
            } else {
                // 拆分成小任务
                int middle = first + (last - first) / 2;
                ForkJoinExample leftTask = new ForkJoinExample(first, middle);
                ForkJoinExample rightTask = new ForkJoinExample(middle + 1, last);
                leftTask.fork();
                rightTask.fork();
                result = leftTask.join() + rightTask.join();
            }
            return result;
        }
    }

    public static class ThreadUnsafeExample {
        private int cnt = 0;

        public void add() {
            cnt++;
        }

        public int get() {
            return cnt;
        }
    }

    public static class ThreadsafeExample {
        private AtomicInteger cnt = new AtomicInteger();

        public void add() {
            cnt.incrementAndGet();
        }

        public int get() {
            return cnt.get();
        }
    }
}
