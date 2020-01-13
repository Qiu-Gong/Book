package com.qiugong.advance.x15.service;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.qiugong.advance.x15.tools.FieldUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author qzx 2019/8/28.
 */
public class ProxyService extends Service {

    public static final String TARGET_SERVICE = "target_service";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null == intent || !intent.hasExtra(TARGET_SERVICE)) {
            return START_STICKY;
        }

        String serviceName = intent.getStringExtra(TARGET_SERVICE);
        if (null == serviceName) {
            return START_STICKY;
        }

        Service targetService;
        try {
            // 获取当前 ActivityThread
            Class activityThreadClazz = Class.forName("android.app.ActivityThread");
            Object activityThread = FieldUtil.getField(activityThreadClazz, null, "sCurrentActivityThread");

            // 执行 getApplicationThread() 得到 applicationThread
            Method getActivityThreadMethod = activityThreadClazz.getDeclaredMethod("getApplicationThread");
            getActivityThreadMethod.setAccessible(true);
            Object applicationThread = getActivityThreadMethod.invoke(activityThread);

            // 再次得到 applicationThread
            Class iInterfaceClazz = Class.forName("android.os.IInterface");
            Method asBinderMethod = iInterfaceClazz.getDeclaredMethod("asBinder");
            asBinderMethod.setAccessible(true);
            Object token = asBinderMethod.invoke(applicationThread);

            // 得到 attach 方法
            Class serviceClazz = Class.forName("android.app.Service");
            Method attachMethod = serviceClazz.getDeclaredMethod("attach",
                    Context.class, activityThreadClazz, String.class,
                    IBinder.class, Application.class, Object.class);
            attachMethod.setAccessible(true);

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

            // 创建 TargetService，执行attach
            //  attach(Context context,
            //         ActivityThread thread, String className, IBinder token,
            //         Application application, Object activityManager)
            targetService = (Service) Class.forName(serviceName).newInstance();
            attachMethod.invoke(targetService, this, activityThread,
                    intent.getComponent().getClassName(), token, getApplication(), iActivityManager);
            targetService.onCreate();
            targetService.onStartCommand(intent, flags, startId);
        } catch (Exception e) {
            e.printStackTrace();
            return START_STICKY;
        }

        return START_STICKY;
    }
}
