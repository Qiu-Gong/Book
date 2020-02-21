package com.qiugong.glide.core;

import android.app.ActivityManager;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import com.qiugong.glide.core.lifecycle.RequestManagerRetriever;
import com.qiugong.glide.core.load.Engine;
import com.qiugong.glide.core.memory.cache.LruResourceCache;
import com.qiugong.glide.core.memory.cache.ResourceCache;
import com.qiugong.glide.core.memory.disk.DiskCache;
import com.qiugong.glide.core.memory.disk.DiskLruCacheWrapper;
import com.qiugong.glide.core.memory.recycle.ArrayPool;
import com.qiugong.glide.core.memory.recycle.BitmapPool;
import com.qiugong.glide.core.memory.recycle.LruArrayPool;
import com.qiugong.glide.core.memory.recycle.LruBitmapPool;
import com.qiugong.glide.core.request.RequestOptions;

import java.util.concurrent.Executor;

/**
 * @author qzx 20/2/19.
 */
class GlideBuilder {

    private static final String TAG = "GlideBuilder";

    private RequestManagerRetriever requestManagerRetriever;
    private RequestOptions requestOptions;
    private Engine engine;
    private Executor executor;

    private ArrayPool arrayPool;
    private BitmapPool bitmapPool;
    private DiskCache diskCache;
    private ResourceCache memoryCache;

    Glide build(Context context) {
        context = context.getApplicationContext();

        requestManagerRetriever = new RequestManagerRetriever();
        requestOptions = new RequestOptions();

        if (executor == null) {
            executor = GlideExecutor.newExecutor();
        }

        if (engine == null) {
            engine = new Engine(context);
        }

        initMemory(context);

        return new Glide(context, this);
    }

    private void initMemory(Context context) {
        long maxSize = getMaxSize(context);

        if (arrayPool == null) {
            arrayPool = new LruArrayPool();
        }

        long availableSize = maxSize - arrayPool.getMaxSize();
        int screenSize = getScreenSizeForRgb(context);

        float bitmapPoolSize = screenSize * 4.0f;
        float memoryCacheSize = screenSize * 2.0f;

        if (bitmapPoolSize + memoryCacheSize <= availableSize) {
            bitmapPoolSize = Math.round(bitmapPoolSize);
            memoryCacheSize = Math.round(memoryCacheSize);
        } else {
            float part = availableSize / 6.0f;
            bitmapPoolSize = Math.round(part * 4);
            memoryCacheSize = Math.round(part * 2);
        }

        if (bitmapPool == null) {
            bitmapPool = new LruBitmapPool(Math.round(bitmapPoolSize));
        }

        if (memoryCache == null) {
            memoryCache = new LruResourceCache(Math.round(memoryCacheSize));
            memoryCache.setResourceRemovedListener(engine);
        }

        if (diskCache == null) {
            diskCache = new DiskLruCacheWrapper(context);
        }

        Log.d(TAG, "initMemory: maxSize = " + (maxSize / 1024 / 1024) + "M" +
                " arrayPoolSize = " + (arrayPool.getMaxSize() / 1024 / 1024) + "M" +
                " bitmapPoolSize = " + (bitmapPool.getMaxSize() / 1024 / 1024) + "M" +
                " memoryCacheSize = " + (memoryCache.getMaxSize() / 1024 / 1024) + "M" +
                " diskCache = " + (diskCache.getMaxSize() / 1024 / 1024) + "M");
    }

    RequestManagerRetriever getRequestManagerRetriever() {
        return requestManagerRetriever;
    }

    RequestOptions getRequestOptions() {
        return requestOptions;
    }

    Engine getEngine() {
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

    private long getMaxSize(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memoryBytes = manager.getMemoryClass() * 1024 * 1024;
        return Math.round(memoryBytes * 0.4);
    }

    private int getScreenSizeForRgb(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels * displayMetrics.heightPixels * 4;
    }
}
