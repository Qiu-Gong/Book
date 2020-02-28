package com.qiugong.tinker;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.qiugong.tinker.core.FixDex;

public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        FixDex.loadFixedDex(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }
}
