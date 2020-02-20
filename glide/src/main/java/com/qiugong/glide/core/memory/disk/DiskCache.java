package com.qiugong.glide.core.memory.disk;

import com.qiugong.glide.core.memory.Key;

import java.io.File;

/**
 * @author qzx 20/2/20.
 */
public interface DiskCache {

    interface Writer {
        boolean write(File file);
    }

    File get(Key key);

    void put(Key key, Writer writer);

    void delete(Key key);

    void clear();
}
