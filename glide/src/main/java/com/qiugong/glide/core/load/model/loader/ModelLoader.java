package com.qiugong.glide.core.load.model.loader;

import com.qiugong.glide.core.load.model.ModelLoaderRegistry;
import com.qiugong.glide.core.load.model.data.LoadData;

/**
 * @author qzx 20/2/22.
 */
public interface ModelLoader<Model, Data> {

    interface ModelLoaderFactory<Model, Data> {
        ModelLoader<Model, Data> build(ModelLoaderRegistry registry);
    }

    LoadData<Data> buildLoadData(Model model);

    boolean handles(Model model);
}
