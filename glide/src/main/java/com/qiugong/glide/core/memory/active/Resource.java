package com.qiugong.glide.core.memory.active;

import android.graphics.Bitmap;
import android.util.Log;

import com.qiugong.glide.core.key.Key;

/**
 * @author qzx 20/2/20.
 */
public class Resource {

    private static final String TAG = "Resource";

    private Bitmap bitmap;
    private int acquired;
    private Key key;
    private ResourceListener resourceListener;

    public interface ResourceListener {
        void onResourceReleased(Key key, Resource resource);
    }

    public Resource(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void acquire() {
        acquired++;
        Log.d(TAG, "acquire: " + acquired);

        if (bitmap.isRecycled()) {
            throw new IllegalStateException("can't acquire because recycled.");
        }
    }

    public void release() {
        acquired--;
        Log.d(TAG, "release: " + acquired);

        if (acquired == 0) {
            resourceListener.onResourceReleased(key, this);
        }
    }

    public void recycle() {
        if (acquired > 0) {
            Log.d(TAG, "recycle failed. acquired:" + acquired);
            return;
        }

        if (!bitmap.isRecycled()) {
            Log.d(TAG, "recycle success.");
            bitmap.recycle();
        } else {
            Log.d(TAG, "recycle failed. is recycled!");
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setResourceListener(Key key, ResourceListener listener) {
        this.key = key;
        this.resourceListener = listener;
    }
}
