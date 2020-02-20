package com.qiugong.glide.core.memory.recycle;

import org.junit.Test;

/**
 * @author qzx 20/2/20.
 */
public class LruArrayPoolTest {

    @Test
    public void testLruPool() {
        LruArrayPool pool = new LruArrayPool();

        byte[] bytes = pool.get(1024 * 1024);
        pool.put(bytes);
        pool.get(1024 * 1000);
    }
}