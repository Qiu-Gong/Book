package com.qiugong.glide.core.load.generator;

/**
 * @author qzx 20/2/22.
 */
public interface DataGenerator {

    boolean startNext();

    void cancel();
}
