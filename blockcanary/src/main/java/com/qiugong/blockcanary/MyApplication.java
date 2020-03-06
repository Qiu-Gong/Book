package com.qiugong.blockcanary;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;

/**
 * @author qzx 20/3/6.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BlockCanary.install(this, new BlockCanaryContext()).start();
    }
}
