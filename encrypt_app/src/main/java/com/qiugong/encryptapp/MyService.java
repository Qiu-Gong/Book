package com.qiugong.encryptapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @author qzx 20/3/4.
 */
public class MyService extends Service {

    private static final String TAG = "MyService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "service:" + getApplication());
        Log.i(TAG, "service:" + getApplicationContext());
        Log.i(TAG, "service:" + getApplicationInfo().className);
    }
}
