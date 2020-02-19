package com.qiugong.glide.core.lifecycle;

import android.content.Context;
import android.util.Log;

/**
 * @author qzx 20/2/19.
 */
class ApplicationLifecycle implements Lifecycle {

    private static final String TAG = "ApplicationLifecycle";

    @Override
    public void addListener(Context context, LifecycleListener listener) {
        Log.d(TAG, "ApplicationLifecycle addListener:" + context);
        listener.onStart();
    }

    @Override
    public void removeListener(Context context, LifecycleListener listener) {
        Log.d(TAG, "ApplicationLifecycle removeListener:" + context);
    }
}
