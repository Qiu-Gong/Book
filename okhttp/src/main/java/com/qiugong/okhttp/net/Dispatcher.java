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
class Dispatcher {

    private static final String TAG = "Dispatcher";
    private int maxRequest;
    private int maxRequestPerHost;

    private ExecutorService executorService;
    private final Deque<Call.AsyncCall> readyAsyncCalls = new ArrayDeque<>();
    private final Deque<Call.AsyncCall> runningAsyncCalls = new ArrayDeque<>();

    Dispatcher() {
        this(64, 2);
    }

    private Dispatcher(int maxRequest, int maxRequestPerHost) {
        this.maxRequest = maxRequest;
        this.maxRequestPerHost = maxRequestPerHost;
    }

    private synchronized ExecutorService executorService() {
        if (executorService == null) {
            ThreadFactory factory = new ThreadFactory() {
                @Override
                public Thread newThread(Runnable runnable) {
                    return new Thread(runnable, "OkHttp Dispatcher");
                }
            };

            executorService = new ThreadPoolExecutor(0,
                    Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), factory);
        }
        return executorService;
    }

    void enqueue(Call.AsyncCall asyncCall) {
        Log.d(TAG, "enqueue: runningAsyncCalls:" + runningAsyncCalls.size());
        Log.d(TAG, "enqueue: runningAsyncCallsPerHost:" + runningAsyncCallsPerHost(asyncCall));

        if (runningAsyncCalls.size() < maxRequest &&
                runningAsyncCallsPerHost(asyncCall) < maxRequestPerHost) {
            Log.d(TAG, "enqueue: dispatch " + asyncCall.toString());
            runningAsyncCalls.add(asyncCall);
            executorService().execute(asyncCall);
        } else {
            Log.d(TAG, "enqueue: readyAsyncCalls" + asyncCall.toString());
            readyAsyncCalls.add(asyncCall);
        }
    }

    void finished(Call.AsyncCall asyncCall) {
        synchronized (this) {
            runningAsyncCalls.remove(asyncCall);
            promoteCalls();
        }
    }

    private void promoteCalls() {
        if (runningAsyncCalls.size() > maxRequest) {
            return;
        }
        if (readyAsyncCalls.isEmpty()) {
            return;
        }

        for (Iterator<Call.AsyncCall> iterator = readyAsyncCalls.iterator();
             iterator.hasNext(); ) {
            Call.AsyncCall call = iterator.next();
            if (runningAsyncCallsPerHost(call) < maxRequestPerHost) {
                iterator.remove();
                runningAsyncCalls.add(call);
                executorService.execute(call);
            }

            // 大于 maxRequest 不在继续执行
            if (runningAsyncCalls.size() >= maxRequest) {
                return;
            }
        }
    }

    private int runningAsyncCallsPerHost(Call.AsyncCall asyncCall) {
        int cnt = 0;
        for (Call.AsyncCall running : runningAsyncCalls) {
            if (running.isSameAddress(asyncCall)) {
                cnt++;
            }
        }
        return cnt;
    }
}
