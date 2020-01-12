package com.qiugong.advance.x15;

import android.app.Application;
import android.content.Context;

import com.qiugong.advance.x15.tools.HookHelper;

/**
 * @author qzx 20/1/6.
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            HookHelper.hookInstrumentation(base);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
