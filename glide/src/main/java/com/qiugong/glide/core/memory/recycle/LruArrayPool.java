package com.qiugong.glide.core.memory.recycle;

import android.util.Log;
import android.util.LruCache;

import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * @author qzx 20/2/20.
 */
public class LruArrayPool implements ArrayPool {

    private static final String TAG = "LruArrayPool";

    // 4M
    private static final int ARRAY_POOL_SIZE_BYTES = 4 * 1024 * 1024;
    private static final int SINGLE_ARRAY_MAX_SIZE_DIVISOR = 2;
    private static final int MAX_OVER_SIZE_MULTIPLE = 8;

    private final int maxSize;
    private LruCache<Integer, byte[]> cache;

    private final NavigableMap<Integer, Integer> sortedSizes = new TreeMap<>();

    public LruArrayPool() {
        this(ARRAY_POOL_SIZE_BYTES);
    }

    private LruArrayPool(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new LruCache<Integer, byte[]>(maxSize) {
            @Override
            protected int sizeOf(Integer key, byte[] value) {
                return value.length;
            }

            @Override
            protected void entryRemoved(boolean evicted, Integer key,
                                        byte[] oldValue, byte[] newValue) {
                Log.d(TAG, "entryRemoved: " + oldValue.length);
                sortedSizes.remove(oldValue.length);
            }
        };
    }

    @Override
    public byte[] get(int len) {
        // 获得大于或者等于buffer
        Integer key = sortedSizes.ceilingKey(len);
        if (key != null) {
            // 不能超过8倍数
            if (key <= (MAX_OVER_SIZE_MULTIPLE * len)) {
                sortedSizes.remove(key);
                byte[] value = cache.remove(key);
                Log.d(TAG, "get form cache, len:" + len + " key:" + key);
                return value == null ? new byte[len] : value;
            }
        }

        Log.d(TAG, "get form new, len:" + len);
        return new byte[len];
    }

    @Override
    public void put(byte[] data) {
        if (data.length > maxSize / SINGLE_ARRAY_MAX_SIZE_DIVISOR) {
            Log.d(TAG, "put not into cache");
            return;
        }

        Log.d(TAG, "put into cache len:" + data.length);
        sortedSizes.put(data.length, 0);
        cache.put(data.length, data);
    }

    @Override
    public void clearMemory() {
        cache.evictAll();
        sortedSizes.clear();
    }

    @Override
    public void trimMemory(int level) {
        if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND) {
            clearMemory();
        } else if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN
                || level == android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL) {
            cache.trimToSize(maxSize / 2);
        }
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }
}
