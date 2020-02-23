package com.qiugong.glide.core.load.model.data;

import java.util.List;

/**
 * @author qzx 20/2/23.
 */
public class MultiFetcher<Data> implements DataFetcher<Data>,
        DataFetcher.DataFetcherListener<Data> {

    private int currentIndex;
    private final List<DataFetcher<Data>> fetchers;
    private DataFetcherListener<? super Data> listener;

    public MultiFetcher(List<DataFetcher<Data>> fetchers) {
        this.fetchers = fetchers;
        this.currentIndex = 0;
    }

    @Override
    public void loadData(DataFetcherListener<? super Data> listener) {
        this.listener = listener;
        fetchers.get(currentIndex).loadData(this);
    }

    @Override
    public void cancel() {
        for (DataFetcher<Data> fetcher : fetchers) {
            fetcher.cancel();
        }
    }

    @Override
    public Class<Data> getDataClass() {
        return fetchers.get(currentIndex).getDataClass();
    }

    @Override
    public void onFetcherReady(Data data) {
        if (data != null) {
            listener.onFetcherReady(data);
        } else {
            startNextOrFail();
        }
    }

    @Override
    public void onLoadFailed(Exception e) {
        startNextOrFail();
    }

    private void startNextOrFail() {
        if (currentIndex < fetchers.size() - 1) {
            currentIndex++;
            loadData(listener);
        } else {
            listener.onLoadFailed(new RuntimeException("Fetch failed"));
        }
    }
}
