package com.qiugong.glide.core;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

import com.qiugong.glide.core.lifecycle.RequestManagerRetriever;
import com.qiugong.glide.core.lifecycle.RequestManager;

/**
 * @author qzx 20/2/19.
 */
public class Glide implements ComponentCallbacks2 {

    private static volatile Glide glide;

    private final Context context;
    private final RequestManagerRetriever requestManagerRetriever;

    Glide(Context context, GlideBuilder builder) {
        this.context = context;
        this.requestManagerRetriever = builder.getRequestManagerRetriever();
    }

    public static RequestManager with(Activity activity) {
        return getRetriever(activity).get(activity);
    }

    public static RequestManager with(androidx.fragment.app.FragmentActivity activity) {
        return getRetriever(activity).get(activity);
    }

    public static RequestManager with(Fragment fragment) {
        return getRetriever(fragment.getActivity()).get(fragment);
    }

    public static RequestManager with(androidx.fragment.app.Fragment fragment) {
        return getRetriever(fragment.getActivity()).get(fragment);
    }

    private static RequestManagerRetriever getRetriever(Context context) {
        return Glide.get(context).getRequestManagerRetriever();
    }

    private RequestManagerRetriever getRequestManagerRetriever() {
        return requestManagerRetriever;
    }

    private static Glide get(Context context) {
        if (glide == null) {
            synchronized (Glide.class) {
                if (glide == null) {
                    initializeGlide(context, new GlideBuilder());
                }
            }
        }
        return glide;
    }

    private static void initializeGlide(Context context, GlideBuilder builder) {
        context.getApplicationContext().registerComponentCallbacks(glide);
        Glide.glide = builder.build(context);
    }

    @Override
    public void onTrimMemory(int level) {
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    }

    @Override
    public void onLowMemory() {
    }
}
