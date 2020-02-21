package com.qiugong.glide.core.memory;

import androidx.annotation.Nullable;

import java.security.MessageDigest;
import java.util.Objects;

/**
 * @author qzx 20/2/21.
 */
public class ObjectKey implements Key {

    private final Object object;

    public ObjectKey(Object object) {
        this.object = object;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(getKeyBytes());
    }

    @Override
    public byte[] getKeyBytes() {
        return toString().getBytes();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(object, ((ObjectKey) obj).object);
    }
}
