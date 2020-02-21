package com.qiugong.glide.core.request;

import com.qiugong.glide.core.memory.active.Resource;

/**
 * @author qzx 20/2/21.
 */
public interface ResourceCallback {
    void onResourceReady(Resource resource);
}
