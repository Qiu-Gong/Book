package com.qiugong.glide.core;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author qzx 20/2/21.
 */
class GlideExecutor {

    private static int bestThreadCount;

    private static int calculateBestThreadCount() {
        if (bestThreadCount == 0) {
            bestThreadCount = Math.min(4, Runtime.getRuntime().availableProcessors());
        }
        return bestThreadCount;
    }

    private static final class DefaultThreadFactory implements ThreadFactory {
        private int threadNum;

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "glide-thread-" + threadNum++);
        }
    }

    static ThreadPoolExecutor newExecutor() {
        int threadCount = calculateBestThreadCount();
        return new ThreadPoolExecutor(
                threadCount, threadCount,
                0, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(),
                new DefaultThreadFactory());
    }
}
