package com.qiugong.glide.core.load.generator;

import android.util.Log;

import com.qiugong.glide.core.Glide;
import com.qiugong.glide.core.load.model.ModelLoader;
import com.qiugong.glide.core.load.model.data.DataFetcher;

import java.util.List;

/**
 * 资源加载器
 *
 * @author qzx 20/2/22.
 */
public class SourceGenerator implements DataGenerator,
        DataFetcher.DataFetcherListener<Object> {

    private static final String TAG = "SourceGenerator";

    private final Glide glide;
    private final DataGeneratorListener dataGeneratorListener;

    private int loadDataListIndex;
    private List<ModelLoader.LoadData<?>> loadDataList;
    private ModelLoader.LoadData<?> loadData;

    public SourceGenerator(Glide glide, Object model, DataGeneratorListener listener) {
        this.glide = glide;
        this.dataGeneratorListener = listener;
        this.loadDataList = glide.getRegistry().getLoadData(model);
    }

    @Override
    public boolean startNext() {
        Log.d(TAG, "源加载器开始加载");
        boolean started = false;
        while (!started && loadDataListIndex < loadDataList.size()) {
            loadData = loadDataList.get(loadDataListIndex++);
            Log.d(TAG, "获得加载设置数据");
            if (loadData != null
                    && glide.getRegistry().hasLoadPath(loadData.fetcher.getDataClass())) {
                Log.d(TAG, "加载设置数据输出数据对应能够查找有效的解码器路径,开始加载数据");
                started = true;
                loadData.fetcher.loadData(this);
            }
        }
        return false;
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
                loadData.sourceKey,
                data,
                DataGeneratorListener.DataSource.REMOTE);
    }

    @Override
    public void onLoadFailed(Exception e) {
        Log.d(TAG, "onLoadFailed: 加载器加载数据失败回调");
        dataGeneratorListener.onDataFetcherFailed(loadData.sourceKey, e);
    }
}
