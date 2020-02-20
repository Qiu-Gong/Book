package com.qiugong.glide.core.memory.cache;

import com.qiugong.glide.core.memory.Key;
import com.qiugong.glide.core.memory.active.Resource;

/**
 * @author qzx 20/2/20.
 */
public interface ResourceCache {

    interface ResourceRemovedListener {
        void onResourceRemoved(Resource resource);
    }

    void setResourceRemovedListener(ResourceRemovedListener listener);

    Resource remove(Key key);

    Resource put(Key key, Resource resource);

    void clearMemory();

    void trimMemory(int level);
}
