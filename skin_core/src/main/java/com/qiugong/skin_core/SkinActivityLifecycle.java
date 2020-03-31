package com.qiugong.skin_core;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.core.view.LayoutInflaterCompat;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qzx 20/3/20.
 */
class SkinActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "SkinActivityLifecycle";

    private Map<Activity, SkinLayoutInflaterFactory> mLayoutInflateFactories = new HashMap<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated:" + activity);

        // 状态栏
        SkinTheme.updateStatusBarColor(activity);
        // 字体
        Typeface typeface = SkinTheme.getSkinTypeface(activity);

        try {
            // mFactorySet 设置后会抛出异常
            LayoutInflater layoutInflater = LayoutInflater.from(activity);
            Field mFactorySetField = LayoutInflater.class.getDeclaredField("mFactorySet");
            mFactorySetField.setAccessible(true);
            mFactorySetField.setBoolean(layoutInflater, false);

            // 设置 LayoutInflaterFactory
            SkinLayoutInflaterFactory inflaterFactory = new SkinLayoutInflaterFactory(activity, typeface);
            LayoutInflaterCompat.setFactory2(layoutInflater, inflaterFactory);

            // 注册
            mLayoutInflateFactories.put(activity, inflaterFactory);
            SkinManager.getInstance().addObserver(inflaterFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG, "onActivityDestroyed:" + activity);
        SkinLayoutInflaterFactory observer = mLayoutInflateFactories.remove(activity);
        SkinManager.getInstance().deleteObserver(observer);
    }
}
