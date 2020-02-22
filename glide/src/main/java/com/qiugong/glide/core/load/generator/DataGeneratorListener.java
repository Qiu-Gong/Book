package com.qiugong.glide.core.load.generator;

import com.qiugong.glide.core.key.Key;

/**
 * @author qzx 20/2/22.
 */
public interface DataGeneratorListener {

    enum DataSource {
        REMOTE,
        CACHE
    }

    /**
     * 加载器加载完成后回调
     */
    void onDataReady(Key key, Object data, DataSource dataSource);

    void onDataFetcherFailed(Key sourceKey, Exception exception);
}
