package com.qiugong.glide.core.load.model;

import com.qiugong.glide.core.key.Key;
import com.qiugong.glide.core.load.model.data.DataFetcher;

/**
 * @author qzx 20/2/22.
 */
public interface ModelLoader<Model, Data> {

    interface ModelLoaderFactory<Model, Data> {
        ModelLoader<Model, Data> build(ModelLoaderRegistry registry);
    }

    class LoadData<Data> {
        public final Key sourceKey;
        public final DataFetcher<Data> fetcher;

        LoadData(Key sourceKey, DataFetcher<Data> fetcher) {
            this.sourceKey = sourceKey;
            this.fetcher = fetcher;
        }
    }

    LoadData<Data> buildLoadData(Model model);

    boolean handles(Model model);
}
