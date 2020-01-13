package com.qiugong.advance.x15.tools;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Build;
import android.os.Handler;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * @author qzx 2019/8/27.
 */
public class HookHelper {

    public static final String TARGET_INTENT = "target_intent";
    public static final String TARGET_INTENT_NAME = "target_intent_name";

    public static void hookAMS() throws Exception {
        Object defaultSingleton;
        if (Build.VERSION.SDK_INT >= 26) {
            // 1. 取出 ActivityManager.IActivityManagerSingleton
            Class<?> activityManageClazz = Class.forName("android.app.ActivityManager");
            defaultSingleton = FieldUtil.getField(activityManageClazz, null, "IActivityManagerSingleton");
        } else {
            // 1. 取出 ActivityManager.gDefault
            Class<?> activityManageClazz = Class.forName("android.app.ActivityManagerNative");
            defaultSingleton = FieldUtil.getField(activityManageClazz, null, "gDefault");
        }

        // 2. 再取出 ActivityManager.IActivityManagerSingleton.Singleton.mInstance
        Class<?> singletonClazz = Class.forName("android.util.Singleton");
        Field mInstanceField = FieldUtil.getField(singletonClazz, "mInstance");
        Object iActivityManager = mInstanceField.get(defaultSingleton);

        // 3. 创建 IActivityManagerProxy
        Class<?> iActivityMangerClazz = Class.forName("android.app.IActivityManager");
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{iActivityMangerClazz}, new IActivityManagerProxy(iActivityManager));

        // 4. mInstance 重新赋值
        mInstanceField.set(defaultSingleton, proxy);
    }

    public static void hookHandler() throws Exception {
        // 1. ActivityThread.sCurrentActivityThread
        Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
        Object currentActivityThread = FieldUtil.getField(activityThreadClazz, null, "sCurrentActivityThread");

        // 2. ActivityThread.mH
        Field mHField = FieldUtil.getField(activityThreadClazz, "mH");
        Handler mH = (Handler) mHField.get(currentActivityThread);

        // 3. 设置新Callback
        FieldUtil.setField(Handler.class, mH, "mCallback", new HCallback(mH));
    }

    public static void hookInstrumentation(Context context) throws Exception {
        // 1. ContextImpl.mMainThread
        Class<?> contextImplClazz = Class.forName("android.app.ContextImpl");
        Field mMainThreadField = FieldUtil.getField(contextImplClazz, "mMainThread");
        Object activityThread = mMainThreadField.get(context);

        // 2. ActivityThread.mInstrumentation
        Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
        Field mInstrumentationField = FieldUtil.getField(activityThreadClazz, "mInstrumentation");

        // 3. 设置新的 mInstrumentation
        FieldUtil.setField(activityThreadClazz, activityThread, "mInstrumentation",
                new InstrumentationProxy((Instrumentation) mInstrumentationField.get(activityThread), context.getPackageManager()));
    }

    public static void hookServiceAMS() throws Exception {
        Object defaultSingleton;
        if (Build.VERSION.SDK_INT >= 26) {
            // 1. 取出 ActivityManager.IActivityManagerSingleton
            Class<?> activityManageClazz = Class.forName("android.app.ActivityManager");
            defaultSingleton = FieldUtil.getField(activityManageClazz, null, "IActivityManagerSingleton");
        } else {
            // 1. 取出 ActivityManager.gDefault
            Class<?> activityManageClazz = Class.forName("android.app.ActivityManagerNative");
            defaultSingleton = FieldUtil.getField(activityManageClazz, null, "gDefault");
        }

        // 2. 再取出 ActivityManager.IActivityManagerSingleton.Singleton.mInstance
        Class<?> singletonClazz = Class.forName("android.util.Singleton");
        Field mInstanceField = FieldUtil.getField(singletonClazz, "mInstance");
        Object iActivityManager = mInstanceField.get(defaultSingleton);

        // 3. 创建 IActivityManagerProxy
        Class<?> iActivityMangerClazz = Class.forName("android.app.IActivityManager");
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{iActivityMangerClazz},
                new IActivityManagerProxy(iActivityManager));

        // 4. 重新赋值
        mInstanceField.set(defaultSingleton, proxy);
    }
}
