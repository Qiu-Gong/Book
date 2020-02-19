package com.qiugong.glide.core.lifecycle;

import android.content.Context;
import android.util.Log;

import com.qiugong.glide.core.lifecycle.Lifecycle;
import com.qiugong.glide.core.lifecycle.LifecycleListener;
import com.qiugong.glide.core.request.RequestTracker;

/**
 * @author qzx 20/2/19.
 */
public class RequestManager implements LifecycleListener {

    private static final String TAG = "RequestManager";

    private Context context;
    private Lifecycle lifecycle;
    private RequestTracker requestTracker;

    public RequestManager(Context context, Lifecycle lifecycle) {
        this.context = context;
        this.lifecycle = lifecycle;
        this.requestTracker = new RequestTracker();
        this.lifecycle.addListener(this.context, this);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart:" + context.toString());
        requestTracker.resumeRequests();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop:" + context.toString());
        requestTracker.pauseRequests();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy:" + context.toString());
        requestTracker.clearRequests();
        lifecycle.removeListener(this.context, this);
    }

    public Context getContext() {
        return context;
    }
}
