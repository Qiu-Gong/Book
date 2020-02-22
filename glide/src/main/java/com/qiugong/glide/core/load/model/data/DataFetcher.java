package com.qiugong.glide.core.load.model.data;

/**
 * @author qzx 20/2/22.
 */
public interface DataFetcher<Data> {

    interface DataFetcherListener<T> {

        void onFetcherReady(T data);

        void onLoadFailed(Exception e);
    }

    Class<Data> getDataClass();

    void loadData(DataFetcherListener<? super Data> listener);

    void cancel();
}
