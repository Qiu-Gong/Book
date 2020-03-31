package com.qiugong.skin_app;

import android.app.Application;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.qiugong.skin_core.SkinManager;

/**
 * @author qzx 20/3/19.
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        SkinManager.init(this);

        boolean isNightMode = NightModeConfig.getInstance().getNightMode(getApplicationContext());
        AppCompatDelegate.setDefaultNightMode(isNightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        Log.d(TAG, isNightMode ? "夜间模式" : "日间模式");
    }
}
