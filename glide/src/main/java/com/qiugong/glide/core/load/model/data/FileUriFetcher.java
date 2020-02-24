package com.qiugong.glide.core.load.model.data;

import android.content.ContentResolver;
import android.net.Uri;

import java.io.InputStream;

/**
 * @author qzx 20/2/23.
 */
public class FileUriFetcher implements DataFetcher<InputStream> {

    private final ContentResolver contentResolver;
    private final Uri uri;

    public FileUriFetcher(Uri uri, ContentResolver contentResolver) {
        this.uri = uri;
        this.contentResolver = contentResolver;
    }

    @Override
    public void loadData(DataFetcherListener<? super InputStream> listener) {
        try {
            InputStream inputStream = contentResolver.openInputStream(uri);
            listener.onFetcherReady(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancel() {
    }

    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }
}
