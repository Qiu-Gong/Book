package com.qiugong.glide.core.memory;

import java.security.MessageDigest;

/**
 * @author qzx 20/2/20.
 */
public interface Key {

    void updateDiskCacheKey(MessageDigest messageDigest);
}
