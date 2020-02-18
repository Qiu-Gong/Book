package com.qiugong.okhttp.net;

import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author qzx 20/2/18.
 */
public class ConnectionPool {

    private static final String TAG = "ConnectionPool";

    private static ThreadFactory threadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "Http Client Connection Pool");
            thread.setDaemon(true);
            return thread;
        }
    };
    private static ExecutorService executor = new ThreadPoolExecutor(0,
            Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), threadFactory);

    private Deque<HttpConnection> connectionDeque = new ArrayDeque<>();
    private final long KEEP_ALIVE_DURATION = 3000L;
    private boolean cleanUpRunning = false;

    public void put(HttpConnection connection) {
        Log.d(TAG, "put connection " + connection.toString());
        if (!cleanUpRunning) {
            cleanUpRunning = true;
            executor.execute(cleanUpRunnable);
        }
        connectionDeque.add(connection);
    }

    public HttpConnection get(String host, int port) {
        Iterator<HttpConnection> iterator = connectionDeque.iterator();
        while (iterator.hasNext()) {
            HttpConnection connection = iterator.next();
            if (connection.isSameAddress(host, port)) {
                iterator.remove();
                Log.d(TAG, "get connection " + connection.toString());
                return connection;
            }
        }
        return null;
    }

    private Runnable cleanUpRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                long waitTime = cleanUp(System.currentTimeMillis());
                if (waitTime == -1) {
                    return;
                }

                if (waitTime > 0) {
                    synchronized (ConnectionPool.this) {
                        try {
                            ConnectionPool.this.wait(waitTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private long cleanUp(long current) {
            long maxIdleDuration = -1;
            synchronized (this) {
                for (Iterator<HttpConnection> iterator = connectionDeque.iterator();
                     iterator.hasNext(); ) {
                    HttpConnection connection = iterator.next();

                    long idleDuration = current - connection.getLastUpdateTime();
                    if (idleDuration > KEEP_ALIVE_DURATION) {
                        connection.closeSocket();
                        iterator.remove();
                        Log.d(TAG, "cleanUp: remove socket:" + connection.toString());
                    }

                    if (maxIdleDuration < idleDuration) {
                        maxIdleDuration = idleDuration;
                    }
                }

                if (maxIdleDuration >= 0) {
                    return KEEP_ALIVE_DURATION - maxIdleDuration;
                } else {
                    cleanUpRunning = false;
                    return maxIdleDuration;
                }
            }
        }
    };
}
