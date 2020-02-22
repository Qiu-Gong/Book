package com.qiugong.glide.core.load;

import com.qiugong.glide.core.key.Key;
import com.qiugong.glide.core.load.codec.LoadPath;
import com.qiugong.glide.core.load.codec.ResourceDecoder;
import com.qiugong.glide.core.load.model.ModelLoader;
import com.qiugong.glide.core.load.model.ModelLoaderRegistry;
import com.qiugong.glide.core.load.codec.ResourceDecoderRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qzx 20/2/22.
 */
public class Registry {

    private final ModelLoaderRegistry modelLoaderRegistry = new ModelLoaderRegistry();
    private final ResourceDecoderRegistry resourceDecoderRegistry = new ResourceDecoderRegistry();

    public <T> void register(Class<T> dataClass, ResourceDecoder<T> decoder) {
        resourceDecoderRegistry.add(dataClass, decoder);
    }

    /**
     * 添加模型加载器
     *
     * @param source  输入类型
     * @param data    输出类型
     * @param factory 加载器工程
     * @param <Model> 输入类型
     * @param <Data>  输出类型
     */
    public <Model, Data> Registry add(Class<Model> source,
                                      Class<Data> data,
                                      ModelLoader.ModelLoaderFactory<Model, Data> factory) {
        modelLoaderRegistry.add(source, data, factory);
        return this;
    }

    /**
     * 获得对应model类型的所有 modelLoader
     */
    public <Model> List<ModelLoader<Model, ?>> getModeLoaders(Model model) {
        Class<Model> modelClass = (Class<Model>) model.getClass();
        return modelLoaderRegistry.getModeLoaders(modelClass);
    }

    /**
     * 获取
     */
    public List<ModelLoader.LoadData<?>> getLoadData(Object model) {
        List<ModelLoader.LoadData<?>> loadData = new ArrayList<>();
        List<ModelLoader<Object, ?>> modelLoaders = getModeLoaders(model);
        for (ModelLoader<Object, ?> modelLoader : modelLoaders) {
            ModelLoader.LoadData<?> current = modelLoader.buildLoadData(model);
            if (current != null) {
                loadData.add(current);
            }
        }
        return loadData;
    }

    public List<Key> getKeys(Object model) {
        List<Key> keys = new ArrayList<>();
        List<ModelLoader.LoadData<?>> loadDataList = getLoadData(model);
        for (ModelLoader.LoadData<?> loadData : loadDataList) {
            keys.add(loadData.sourceKey);
        }
        return keys;
    }

    <Data> LoadPath<Data> getLoadPath(Class<Data> dataClass) {
        List<ResourceDecoder<Data>> decoders = resourceDecoderRegistry.getDecoders(dataClass);
        return new LoadPath<>(decoders);
    }

    public boolean hasLoadPath(Class<?> dataClass) {
        return getLoadPath(dataClass) != null;
    }

}
