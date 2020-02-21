package com.qiugong.glide.core.key;

import java.security.MessageDigest;

/**
 * @author qzx 20/2/20.
 */
public interface Key {

    void updateDiskCacheKey(MessageDigest messageDigest);

    byte[] getKeyBytes();
}
