package com.qiugong.glide.core.memory.disk;

import android.content.Context;
import android.util.Log;

import com.qiugong.glide.core.Utils;
import com.qiugong.glide.core.memory.Key;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * @author qzx 20/2/20.
 */
public class DiskLruCacheWrapper implements DiskCache {

    private static final String TAG = "DiskLruCacheWrapper";

    private static final int DEFAULT_DISK_CACHE_SIZE = 250 * 1024 * 1024;
    private static final String DEFAULT_DISK_CACHE_DIR = "disk_cache";

    private MessageDigest messageDigest;
    private DiskLruCache diskLruCache;

    public DiskLruCacheWrapper(Context context) {
        this(new File(context.getCacheDir(), DEFAULT_DISK_CACHE_DIR), DEFAULT_DISK_CACHE_SIZE);
    }

    public DiskLruCacheWrapper(File dir, long maxSize) {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            diskLruCache = DiskLruCache.open(dir, 1, 1, maxSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public File get(Key key) {
        try {
            String k = getKey(key);
            Log.d(TAG, "get: key = " + k);
            DiskLruCache.Value value = diskLruCache.get(k);
            if (value != null) {
                return value.getFile(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void put(Key key, Writer writer) {
        String k = getKey(key);
        Log.d(TAG, "put: key = " + k);
        try {
            if (diskLruCache.get(k) != null) {
                return;
            }

            DiskLruCache.Editor edit = diskLruCache.edit(k);
            File file = edit.getFile(0);
            if (writer.write(file)) {
                edit.commit();
            }
            edit.abortUnlessCommitted();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Key key) {
        String k = getKey(key);
        try {
            diskLruCache.remove(k);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        try {
            diskLruCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getKey(Key key) {
        key.updateDiskCacheKey(messageDigest);
        return Utils.sha256BytesToHex(messageDigest.digest());
    }
}
