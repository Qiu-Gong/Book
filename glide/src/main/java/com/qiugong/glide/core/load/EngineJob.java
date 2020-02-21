package com.qiugong.glide.core.load;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.qiugong.glide.core.Glide;
import com.qiugong.glide.core.key.EngineKey;
import com.qiugong.glide.core.memory.active.Resource;
import com.qiugong.glide.core.request.ResourceCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;


/**
 * @author qzx 20/2/21.
 * @see #start 开始执行Job，转而到 DecodeJob 中执行，执行完后会调用 onResourceReady，
 * 通知执行完成。主线程切换 回调用 Engine.onEngineJobComplete 进行内存管理，接着 Request.onResourceReady
 * 刷新图片。
 */
public class EngineJob implements DecodeJobListener {

    private static final String TAG = "EngineJob";

    private static final int MSG_COMPLETE = 1;
    private static final int MSG_EXCEPTION = 2;
    private static final int MSG_CANCELLED = 3;
    private static final Handler MAIN_THREAD_HANDLER =
            new Handler(Looper.getMainLooper(), new MainThreadCallback());

    private Resource resource;
    private EngineKey key;
    private List<ResourceCallback> resourceCallbacks = new ArrayList<>();
    private Executor executor;
    private EngineJobListener engineJobListener;
    private DecodeJob decodeJob;
    private boolean isCancelled;

    EngineJob(Context context, EngineKey engineKey, EngineJobListener listener) {
        this.key = engineKey;
        this.executor = Glide.get(context).getExecutor();
        this.engineJobListener = listener;
    }

    void start(DecodeJob decodeJob) {
        Log.d(TAG, "start: decodeJob = " + decodeJob);
        this.decodeJob = decodeJob;
        executor.execute(decodeJob);
    }

    private void cancel() {
        Log.d(TAG, "cancel");
        isCancelled = true;
        decodeJob.cancel();
        engineJobListener.onEngineJobCancelled(this, key);
    }

    private void release() {
        Log.d(TAG, "release");
        resourceCallbacks.clear();
        key = null;
        resource = null;
        isCancelled = false;
        decodeJob = null;
    }

    void addCallback(ResourceCallback callback) {
        Log.d(TAG, "addCallback");
        resourceCallbacks.add(callback);
    }

    void removeCallback(ResourceCallback callback) {
        Log.d(TAG, "removeCallback");
        resourceCallbacks.remove(callback);
        if (resourceCallbacks.isEmpty()) {
            cancel();
        }
    }

    @Override
    public void onResourceReady(Resource resource) {
        this.resource = resource;
        MAIN_THREAD_HANDLER.obtainMessage(MSG_COMPLETE, this).sendToTarget();
    }

    @Override
    public void onLoadFailed(Throwable e) {
        MAIN_THREAD_HANDLER.obtainMessage(MSG_EXCEPTION, this).sendToTarget();
    }

    private static class MainThreadCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            EngineJob job = (EngineJob) msg.obj;
            switch (msg.what) {
                case MSG_COMPLETE:
                    job.handleResultOnMainThread();
                    break;
                case MSG_EXCEPTION:
                    job.handleExceptionOnMainThread();
                    break;
                case MSG_CANCELLED:
                    job.handleCancelledOnMainThread();
                    break;
                default:
                    throw new IllegalStateException("Unrecognized message: " + msg.what);
            }
            return true;
        }
    }

    private void handleResultOnMainThread() {
        if (isCancelled) {
            resource.recycle();
            release();
        }
        resource.acquire();
        engineJobListener.onEngineJobComplete(this, key, resource);
        for (int i = 0; i < resourceCallbacks.size(); i++) {
            ResourceCallback callback = resourceCallbacks.get(i);
            resource.acquire();
            callback.onResourceReady(resource);
        }
        resource.release();
        release();
    }

    private void handleCancelledOnMainThread() {
        engineJobListener.onEngineJobCancelled(this, key);
        release();
    }

    private void handleExceptionOnMainThread() {
        if (isCancelled) {
            release();
            return;
        }

        engineJobListener.onEngineJobComplete(this, key, null);
        for (ResourceCallback callback : resourceCallbacks) {
            callback.onResourceReady(null);
        }
    }
}
