package com.qiugong.advance.x14.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author qzx 20/1/12.
 */
public class InstrumentationProxy extends Instrumentation {

    private static final String TAG = "InstrumentationProxy";
    Instrumentation mInstrumentation;

    public InstrumentationProxy(Instrumentation instrumentation) {
        this.mInstrumentation = instrumentation;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        Log.d(TAG, "Hook 成功" + "---who:" + who);

        try {
            Method execStartActivity = Instrumentation.class.getDeclaredMethod(
                    "execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Activity.class,
                    Intent.class, int.class, Bundle.class);
            return (ActivityResult) execStartActivity.invoke(mInstrumentation, who,
                    contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void replaceActivityInstrumentation(Activity activity) {
        // Activity.mInstrumentation
        try {
            // 1. 取出 Activity.mInstrumentation
            Field field = Activity.class.getDeclaredField("mInstrumentation");
            field.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) field.get(activity);

            // 2. 重新创建 instrumentationProxy
            Instrumentation instrumentationProxy = new InstrumentationProxy(instrumentation);

            // 3. 赋值 Activity.mInstrumentation = instrumentationProxy
            field.set(activity, instrumentationProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void replaceContextInstrumentation() {
        // ActivityThread = ActivityThread.sCurrentActivityThread
        // ActivityThread.mInstrumentation
        try {
            // 1. ActivityThread.Class
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");

            // 2. 取出 currentActivityThread=ActivityThread.sCurrentActivityThread
            Field activityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            activityThreadField.setAccessible(true);
            Object currentActivityThread = activityThreadField.get(null);

            // 3. mInstrumentation = ActivityThread.mInstrumentation
            Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

            // 4. 重新创建 instrumentationProxy
            Instrumentation instrumentationProxy = new InstrumentationProxy(mInstrumentation);

            // 5. 赋值 ActivityThread.mInstrumentation = instrumentationProxy
            mInstrumentationField.set(currentActivityThread, instrumentationProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
