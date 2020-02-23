package com.qiugong.glide.core.load.model.data;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author qzx 20/2/23.
 */
public class HttpUriFetcher implements DataFetcher<InputStream> {

    private final Uri uri;
    private boolean isCancelled;

    public HttpUriFetcher(Uri uri) {
        this.uri = uri;
    }

    @Override
    public void loadData(DataFetcherListener<? super InputStream> listener) {
        HttpURLConnection connection = null;
        InputStream stream = null;
        int responseCode;

        try {
            connection = (HttpURLConnection) new URL((uri.toString())).openConnection();
            connection.connect();
            stream = connection.getInputStream();
            responseCode = connection.getResponseCode();
            if (isCancelled) {
                return;
            }

            if (responseCode == 200) {
                listener.onFetcherReady(stream);
            } else {
                listener.onLoadFailed(new RuntimeException(connection.getResponseMessage()));
            }

        } catch (Exception e) {
            e.printStackTrace();
            listener.onLoadFailed(e);

        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    public void cancel() {
        isCancelled = true;
    }

    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }
}
