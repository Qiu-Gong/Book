package com.qiugong.glide.core.lifecycle;

import android.content.Context;

/**
 * @author qzx 20/2/19.
 */
public interface Lifecycle {

    void addListener(Context context, LifecycleListener listener);

    void removeListener(Context context, LifecycleListener listener);
}
