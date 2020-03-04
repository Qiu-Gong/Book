package com.qiugong.encryptapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author qzx 20/3/4.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "receiver:" + context);
        Log.i(TAG,"receiver:" + context.getApplicationContext());
        Log.i(TAG,"receiver:" + context.getApplicationInfo().className);
    }
}
