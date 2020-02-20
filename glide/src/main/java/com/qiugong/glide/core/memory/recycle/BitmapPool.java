package com.qiugong.glide.core.memory.recycle;

import android.graphics.Bitmap;

/**
 * @author qzx 20/2/20.
 */
public interface BitmapPool {

    void put(Bitmap bitmap);

    Bitmap get(int width, int height, Bitmap.Config config);

    void clearMemory();

    void trimMemory(int level);
}
