package com.qiugong.glide.core.lifecycle;

import android.content.Context;
import android.util.Log;

import com.qiugong.glide.core.request.ModelTypes;
import com.qiugong.glide.core.request.Request;
import com.qiugong.glide.core.request.RequestBuilder;

import java.io.File;

/**
 * @author qzx 20/2/19.
 */
public class RequestManager implements LifecycleListener, ModelTypes {

    private static final String TAG = "RequestManager";

    private Context context;
    private Lifecycle lifecycle;
    private RequestTracker requestTracker;

    RequestManager(Context context, Lifecycle lifecycle) {
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

    public void track(Request request) {
        requestTracker.runRequest(request);
    }

    public RequestBuilder asBitmap() {
        return new RequestBuilder(context, this);
    }

    @Override
    public RequestBuilder load(String path) {
        return asBitmap().load(path);
    }

    @Override
    public RequestBuilder load(File file) {
        return asBitmap().load(file);
    }

    public Context getContext() {
        return context;
    }
}
