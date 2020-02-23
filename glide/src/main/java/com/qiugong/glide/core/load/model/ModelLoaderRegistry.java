package com.qiugong.glide.core.load.model;

import com.qiugong.glide.core.load.model.loader.ModelLoader;

import java.util.List;

/**
 * @author qzx 20/2/22.
 */
public class ModelLoaderRegistry {

    public <Model, Data> void add(Class<Model> source, Class<Data> data, ModelLoader.ModelLoaderFactory<Model, Data> factory) {

    }

    public <Model> List<ModelLoader<Model, ?>> getModeLoaders(Class<Model> modelClass) {
        return null;
    }
}
