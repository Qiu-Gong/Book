package com.qiugong.glide.core.load;

import com.qiugong.glide.core.memory.active.Resource;

/**
 * @author qzx 20/2/22.
 */
public interface DecodeJobListener {

    void onResourceReady(Resource resource);

    void onLoadFailed(Throwable e);
}
