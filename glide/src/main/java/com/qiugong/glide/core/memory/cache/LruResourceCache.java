package com.qiugong.glide.core.memory.cache;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.qiugong.glide.core.key.Key;
import com.qiugong.glide.core.memory.active.Resource;

/**
 * @author qzx 20/2/20.
 */
public class LruResourceCache implements ResourceCache {

    private final int maxSize;
    private final LruCache<Key, Resource> cache;

    private ResourceRemovedListener resourceRemovedListener;
    private boolean isSelfRemove;

    public LruResourceCache(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new LruCache<Key, Resource>(maxSize) {
            @Override
            protected int sizeOf(Key key, Resource value) {
                return getSize(value.getBitmap());
            }

            @Override
            protected void entryRemoved(boolean evicted, Key key,
                                        Resource oldValue, Resource newValue) {
                if (resourceRemovedListener != null && !isSelfRemove) {
                    resourceRemovedListener.onResourceRemoved(oldValue);
                }
            }
        };
    }

    @Override
    public void setResourceRemovedListener(ResourceRemovedListener listener) {
        this.resourceRemovedListener = listener;
    }

    @Override
    public Resource remove(Key key) {
        isSelfRemove = true;
        Resource value = cache.remove(key);
        isSelfRemove = false;
        return value;
    }

    @Override
    public Resource put(Key key, Resource resource) {
        return cache.put(key, resource);
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public void clearMemory() {
        cache.evictAll();
    }

    @Override
    public void trimMemory(int level) {
        if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND) {
            clearMemory();
        } else if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            cache.trimToSize(maxSize / 2);
        }
    }

    private int getSize(Bitmap value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return value.getAllocationByteCount();
        } else {
            return value.getByteCount();
        }
    }
}
