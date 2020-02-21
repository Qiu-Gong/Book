package com.qiugong.glide.core.memory.recycle;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.util.LruCache;

import androidx.annotation.Nullable;

/**
 * @author qzx 20/2/20.
 */
public class LruBitmapPool implements BitmapPool {

    private static final String TAG = "LruBitmapPool";

    private final int maxSize;
    private final LruCache<Key, Bitmap> cache;

    public LruBitmapPool(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new LruCache<Key, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(Key key, Bitmap value) {
                return 0;
            }

            @Override
            protected void entryRemoved(boolean evicted, Key key, Bitmap oldValue, Bitmap newValue) {
                Log.d(TAG, "entryRemoved: ");
            }
        };
    }

    @Override
    public void put(Bitmap bitmap) {
        if (!bitmap.isMutable()) {
            Log.d(TAG, "put failed isMutable false.");
            bitmap.recycle();
            return;
        }

        int size = getSize(bitmap);
        if (size >= maxSize) {
            Log.d(TAG, "put failed maxSize:" + maxSize + " size:" + size);
            bitmap.recycle();
            return;
        }

        Log.d(TAG, "put into cache. size:" + size);
        Key key = new Key(size, bitmap.getConfig());
        cache.put(key, bitmap);
    }

    @Override
    public Bitmap get(int width, int height, Bitmap.Config config) {
        Key key = new Key(getSize(width, height, config), config);
        Bitmap value = cache.remove(key);
        Log.d(TAG, "get: bitmap=" + value);
        return value;
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

    private int getSize(int width, int height, Bitmap.Config config) {
        return width * height * (config == Bitmap.Config.ARGB_8888 ? 4 : 2);
    }

    private class Key {
        private int size;
        private Bitmap.Config config;

        Key(int size, Bitmap.Config config) {
            this.size = size;
            this.config = config;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;

            Key key = (Key) object;
            return (config == key.config) && (size == key.size);
        }

        @Override
        public int hashCode() {
            return 31 * size + (config != null ? config.hashCode() : 0);
        }
    }
}
