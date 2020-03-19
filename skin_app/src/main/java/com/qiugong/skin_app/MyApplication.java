package com.qiugong.skin_app;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * @author qzx 20/3/19.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        boolean isNightMode = NightModeConfig.getInstance().getNightMode(getApplicationContext());
//        AppCompatDelegate.setDefaultNightMode(isNightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
}
