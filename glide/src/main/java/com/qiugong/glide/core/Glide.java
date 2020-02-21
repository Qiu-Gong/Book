package com.qiugong.glide.core;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

import com.qiugong.glide.core.lifecycle.RequestManager;
import com.qiugong.glide.core.lifecycle.RequestManagerRetriever;
import com.qiugong.glide.core.load.Engine;
import com.qiugong.glide.core.memory.cache.ResourceCache;
import com.qiugong.glide.core.memory.disk.DiskCache;
import com.qiugong.glide.core.memory.recycle.ArrayPool;
import com.qiugong.glide.core.memory.recycle.BitmapPool;
import com.qiugong.glide.core.request.RequestOptions;

import java.util.concurrent.Executor;

/**
 * @author qzx 20/2/19.
 */
public class Glide implements ComponentCallbacks2 {

    private static volatile Glide glide;

    private final Context context;
    private final RequestManagerRetriever requestManagerRetriever;
    private final RequestOptions requestOptions;
    private final Engine engine;
    private Executor executor;

    private ArrayPool arrayPool;
    private BitmapPool bitmapPool;
    private DiskCache diskCache;
    private ResourceCache memoryCache;

    Glide(Context context, GlideBuilder builder) {
        this.context = context;
        this.requestManagerRetriever = builder.getRequestManagerRetriever();
        this.requestOptions = builder.getRequestOptions();
        this.engine = builder.getEngine();
        this.executor = builder.getExecutor();
        this.arrayPool = builder.getArrayPool();
        this.bitmapPool = builder.getBitmapPool();
        this.diskCache = builder.getDiskCache();
        this.memoryCache = builder.getMemoryCache();
    }

    public static Glide get(Context context) {
        if (glide == null) {
            synchronized (Glide.class) {
                if (glide == null) {
                    initializeGlide(context, new GlideBuilder());
                }
            }
        }
        return glide;
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

    private static void initializeGlide(Context context, GlideBuilder builder) {
        context.getApplicationContext().registerComponentCallbacks(glide);
        Glide.glide = builder.build(context);
    }

    public RequestOptions getRequestOptions() {
        return requestOptions;
    }

    public Engine getEngine() {
        return engine;
    }

    public Executor getExecutor() {
        return executor;
    }

    public ArrayPool getArrayPool() {
        return arrayPool;
    }

    public BitmapPool getBitmapPool() {
        return bitmapPool;
    }

    public DiskCache getDiskCache() {
        return diskCache;
    }

    public ResourceCache getMemoryCache() {
        return memoryCache;
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
