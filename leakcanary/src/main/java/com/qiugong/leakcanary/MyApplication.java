package com.qiugong.leakcanary;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * @author qzx 20/3/6.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
