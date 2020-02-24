package com.qiugong.glide.core.load.generator;

import android.util.Log;

import com.qiugong.glide.core.Glide;
import com.qiugong.glide.core.key.Key;
import com.qiugong.glide.core.load.model.loader.ModelLoader;
import com.qiugong.glide.core.load.model.data.DataFetcher;
import com.qiugong.glide.core.load.model.data.LoadData;

import java.io.File;
import java.util.List;

/**
 * 磁盘加载器
 *
 * @author qzx 20/2/22.
 */
public class DataCacheGenerator implements DataGenerator,
        DataFetcher.DataFetcherListener<Object> {

    private static final String TAG = "DataCacheGenerator";

    private final Glide glide;
    private final DataGeneratorListener dataGeneratorListener;

    private List<Key> keys;
    private Key sourceKey;
    private int sourceIdIndex = -1;

    private List<ModelLoader<File, ?>> modelLoaders;
    private LoadData<?> loadData;
    private File cacheFile;
    private int modelLoaderIndex;

    public DataCacheGenerator(Glide glide, Object model, DataGeneratorListener listener) {
        this.glide = glide;
        this.dataGeneratorListener = listener;
        this.keys = glide.getRegistry().getKeys(model);
    }

    @Override
    public boolean startNext() {
        Log.d(TAG, "磁盘加载器开始加载");
        while (modelLoaders == null) {
            sourceIdIndex++;
            if (sourceIdIndex >= keys.size()) {
                return false;
            }

            Key sourceId = keys.get(sourceIdIndex);
            cacheFile = glide.getDiskCache().get(sourceId);
            Log.d(TAG, "startNext: 磁盘缓存存在则位于 " + cacheFile);
            if (cacheFile != null) {
                sourceKey = sourceId;
                modelLoaders = glide.getRegistry().getModeLoaders(cacheFile);
                modelLoaderIndex = 0;
            }
        }

        boolean started = false;
        while (!started && modelLoaderIndex < modelLoaders.size()) {
            ModelLoader<File, ?> modelLoader = modelLoaders.get(modelLoaderIndex++);
            loadData = modelLoader.buildLoadData(cacheFile);
            Log.d(TAG, "获得加载设置数据");
            if (loadData != null
                    && glide.getRegistry().hasLoadPath(loadData.fetcher.getDataClass())) {
                Log.d(TAG, "startNext: 加载设置数据输出数据对应能够查找有效的解码器路径,开始加载数据");
                started = true;
                loadData.fetcher.loadData(this);
            }
        }
        return started;
    }

    @Override
    public void cancel() {
        if (loadData != null) {
            loadData.fetcher.cancel();
        }
    }

    @Override
    public void onFetcherReady(Object data) {
        Log.d(TAG, "onFetcherReady: 加载器加载数据成功回调");
        dataGeneratorListener.onDataReady(
                sourceKey,
                data,
                DataGeneratorListener.DataSource.REMOTE);
    }

    @Override
    public void onLoadFailed(Exception e) {
        Log.d(TAG, "onLoadFailed: 加载器加载数据失败回调");
        dataGeneratorListener.onDataFetcherFailed(loadData.sourceKey, e);
    }
}
