package com.qiugong.artisticprobes.x2;

import android.app.Application;
import android.util.Log;

/**
 * @author qzx 20/1/6.
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }
}
