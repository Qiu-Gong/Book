package com.qiugong.skin_core;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * @author qzx 20/3/20.
 */
class SkinLayoutInflaterFactory implements LayoutInflater.Factory2, Observer {

    private static final String TAG = "SkinLayoutInflater";

    private static final String[] CLASS_PRE_FIX = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    private static final Class<?>[] mConstructorSignature = new Class[]{Context.class, AttributeSet.class};
    private static final Map<String, Constructor<? extends View>> mConstructorMap = new HashMap<>();

    private Activity activity;
    private SkinAttribute skinAttribute;

    SkinLayoutInflaterFactory(Activity activity, Typeface typeface) {
        this.activity = activity;
        skinAttribute = new SkinAttribute(typeface);
    }

    /**
     * @param parent  当前TAG 父布局
     * @param name    在布局中的TAG 如:TextView, android.support.v7.widget.Toolbar
     * @param context 上下文
     * @param attrs   对应布局TAG中的属性 如: android:text android:src
     */
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = createViewFromTag(name, context, attrs);
        if (view == null) {
            view = createView(name, context, attrs);
        }

        if (view != null) {
            Log.d(TAG, "onCreateView: 检查[" + context.getClass().getName() + "]:" + name);
            skinAttribute.load(view, attrs);
        }

        return view;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        Log.d(TAG, "update:" + arg);
//        SkinTheme.updateStatusBarColor(activity);
        Typeface typeface = SkinTheme.getSkinTypeface(activity);
        skinAttribute.setTypeface(typeface);
        skinAttribute.applySkin();
    }

    private View createViewFromTag(String name, Context context, AttributeSet attrs) {
        if (name.indexOf('.') != -1) {
            return null;
        }

        for (String classPreFix : CLASS_PRE_FIX) {
            View view = createView(classPreFix + name, context, attrs);
            if (view != null) {
                return view;
            }
        }

        return null;
    }

    private View createView(String name, Context context, AttributeSet attrs) {
        try {
            return findConstructor(context, name).newInstance(context, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Constructor<? extends View> findConstructor(Context context, String name) throws Exception {
        Constructor<? extends View> constructor = mConstructorMap.get(name);
        if (constructor == null) {
            Class<? extends View> clazz = context.getClassLoader().loadClass(name).asSubclass(View.class);
            constructor = clazz.getConstructor(mConstructorSignature);
            mConstructorMap.put(name, constructor);
        }
        return constructor;
    }
}
