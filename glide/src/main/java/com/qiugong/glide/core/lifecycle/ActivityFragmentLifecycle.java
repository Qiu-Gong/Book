package com.qiugong.glide.core.lifecycle;

import android.content.Context;
import android.util.Log;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * @author qzx 20/2/19.
 */
class ActivityFragmentLifecycle implements Lifecycle, LifecycleListener {

    private static final String TAG = "ActivityFragmentLife";

    private static final int DEFAULT = 0;
    private static final int START = 1;
    private static final int STOP = 2;
    private static final int DESTROY = 3;

    // 使用 弱引用 HashMap，但是没有 弱引用的 Set，所以只能用以下方法
    private final Set<LifecycleListener> lifecycleListeners
            = Collections.newSetFromMap(new WeakHashMap<LifecycleListener, Boolean>());

    private Context context;
    private int status = DEFAULT;

    @Override
    public void addListener(Context context, LifecycleListener listener) {
        Log.d(TAG, "ActivityFragmentLifecycle addListener:" + context);

        this.context = context;
        lifecycleListeners.add(listener);

        if (status == DESTROY) {
            listener.onDestroy();
        } else if (status == START) {
            listener.onStart();
        } else if (status == STOP) {
            listener.onStop();
        }
    }

    @Override
    public void removeListener(Context context, LifecycleListener listener) {
        Log.d(TAG, "ActivityFragmentLifecycle removeListener:" + context);
        this.context = null;
        lifecycleListeners.remove(listener);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "ActivityFragmentLifecycle onStart:" + context);
        status = START;
        for (LifecycleListener listener : lifecycleListeners) {
            listener.onStart();
        }
    }

    @Override
    public void onStop() {
        Log.d(TAG, "ActivityFragmentLifecycle onStop:" + context);
        status = STOP;
        for (LifecycleListener listener : lifecycleListeners) {
            listener.onStop();
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "ActivityFragmentLifecycle onDestroy:" + context);
        status = DESTROY;
        for (LifecycleListener listener : lifecycleListeners) {
            listener.onDestroy();
        }
    }
}
