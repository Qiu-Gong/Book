package com.qiugong.glide.core.load.model.loader;

import android.net.Uri;

import com.qiugong.glide.core.key.ObjectKey;
import com.qiugong.glide.core.load.model.ModelLoaderRegistry;
import com.qiugong.glide.core.load.model.data.HttpUriFetcher;
import com.qiugong.glide.core.load.model.data.LoadData;

import java.io.InputStream;

/**
 * @author qzx 20/2/23.
 */
public class HttpUriLoader implements ModelLoader<Uri, InputStream> {

    @Override
    public LoadData<InputStream> buildLoadData(Uri uri) {
        return new LoadData<>(new ObjectKey(uri), new HttpUriFetcher(uri));
    }

    @Override
    public boolean handles(Uri uri) {
        String scheme = uri.getScheme();
        return scheme.equalsIgnoreCase("http") ||
                scheme.equalsIgnoreCase("https");
    }

    public static class Factory implements ModelLoaderFactory<Uri, InputStream> {
        @Override
        public ModelLoader<Uri, InputStream> build(ModelLoaderRegistry registry) {
            return new HttpUriLoader();
        }
    }
}
