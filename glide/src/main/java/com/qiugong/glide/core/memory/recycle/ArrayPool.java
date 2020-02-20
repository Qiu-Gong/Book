package com.qiugong.glide.core.memory.recycle;

/**
 * @author qzx 20/2/20.
 */
public interface ArrayPool {

    byte[] get(int len);

    void put(byte[] data);

    void clearMemory();

    void trimMemory(int level);

    int getMaxSize();
}
