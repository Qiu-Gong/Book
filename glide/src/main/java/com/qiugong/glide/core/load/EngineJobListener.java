package com.qiugong.glide.core.load;

import com.qiugong.glide.core.key.Key;
import com.qiugong.glide.core.memory.active.Resource;

/**
 * @author qzx 20/2/22.
 */
public interface EngineJobListener {

    void onEngineJobComplete(EngineJob engineJob, Key key, Resource resource);

    void onEngineJobCancelled(EngineJob engineJob, Key key);
}
