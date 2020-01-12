package com.qiugong.advance.x15.tools;

import android.content.Intent;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author qzx 2019/8/27.
 */
public class IActivityManagerProxy implements InvocationHandler {

    private static final String TAG = "IActivityManagerProxy";
    private Object mActivityManager;

    public IActivityManagerProxy(Object mActivityManager) {
        this.mActivityManager = mActivityManager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d(TAG, "hook name:" + method.getName());
        if ("startActivity".equals(method.getName())) {
            Log.d(TAG, "hook success");
            Intent intent;
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }

            intent = (Intent) args[index];
            Intent subIntent = new Intent();
            String packagerName = "com.qiugong.book";
            subIntent.setClassName(packagerName,  "com.qiugong.advance.x15.activity.StubActivity");
            subIntent.putExtra(HookHelper.TARGET_INTENT, intent);
            args[index] = subIntent;
        }

        return method.invoke(mActivityManager, args);
    }
}
