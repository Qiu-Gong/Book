package com.qiugong.glide.core.key;

import androidx.annotation.Nullable;

import java.security.MessageDigest;
import java.util.Objects;

/**
 * @author qzx 20/2/21.
 */
public class EngineKey implements Key {

    private final Object object;
    private final int width;
    private final int height;

    public EngineKey(Object object, int width, int height) {
        this.object = object;
        this.width = width;
        this.height = height;
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
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        EngineKey engineKey = (EngineKey) obj;

        if (width != engineKey.width) return false;
        if (height != engineKey.height) return false;
        if (hashCode() != engineKey.hashCode()) return false;
        return Objects.equals(object, engineKey.object);
    }

    @Override
    public int hashCode() {
        int result = object != null ? object.hashCode() : 0;
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result;
        return result;
    }

    @Override
    public String toString() {
        return "EngineKey{" +
                "object=" + object +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
