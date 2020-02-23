package com.qiugong.glide.core.load.model.data;

import com.qiugong.glide.core.key.Key;

/**
 * @author qzx 20/2/23.
 */
public class LoadData<Data> {

    public final Key sourceKey;
    public final DataFetcher<Data> fetcher;

    public LoadData(Key sourceKey, DataFetcher<Data> fetcher) {
        this.sourceKey = sourceKey;
        this.fetcher = fetcher;
    }
}
