package com.qiugong.glide.core.load.model;

import com.qiugong.glide.core.load.model.loader.ModelLoader;
import com.qiugong.glide.core.load.model.loader.MultiModelLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qzx 20/2/22.
 */
public class ModelLoaderRegistry {

    private static class Entry<Model, Data> {

        private final Class<Model> modelClass;
        private final Class<Data> dataClass;
        final ModelLoader.ModelLoaderFactory<? extends Model, ? extends Data> factory;

        Entry(
                Class<Model> modelClass,
                Class<Data> dataClass,
                ModelLoader.ModelLoaderFactory<? extends Model, ? extends Data> factory) {
            this.modelClass = modelClass;
            this.dataClass = dataClass;
            this.factory = factory;
        }

        boolean handles(Class<?> modelClass, Class<?> dataClass) {
            return handles(modelClass) && this.dataClass.isAssignableFrom(dataClass);
        }

        boolean handles(Class<?> modelClass) {
            return this.modelClass.isAssignableFrom(modelClass);
        }
    }

    private final List<Entry<?, ?>> entries = new ArrayList<>();

    public synchronized <Model, Data> void add(
            Class<Model> modelClass,
            Class<Data> dataClass,
            ModelLoader.ModelLoaderFactory<? extends Model, ? extends Data> factory) {
        entries.add(new Entry<>(modelClass, dataClass, factory));
    }

    public synchronized <Model, Data> ModelLoader<Model, Data> build(
            Class<Model> modelClass,
            Class<Data> dataClass) {
        List<ModelLoader<Model, Data>> loaders = new ArrayList<>();
        for (Entry<?, ?> entry : entries) {
            if (entry.handles(modelClass, dataClass)) {
                loaders.add((ModelLoader<Model, Data>) entry.factory.build(this));
            }
        }
        if (loaders.size() > 1) {
            return new MultiModelLoader<>(loaders);
        } else if (loaders.size() == 1) {
            return loaders.get(0);
        }

        throw new RuntimeException("No Have:" + modelClass.getName() + " Model Match " +
                dataClass.getName() + " Data");
    }

    public <Model> List<ModelLoader<Model, ?>> getModeLoaders(Class<Model> modelClass) {
        List<ModelLoader<Model, ?>> modelLoaders = new ArrayList<>();
        for (Entry<?, ?> entry : entries) {
            if (entry.handles(modelClass)) {
                modelLoaders.add((ModelLoader<Model, ?>) entry.factory.build(this));
            }
        }
        return modelLoaders;
    }
}
