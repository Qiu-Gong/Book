package com.qiugong.glide.core.load.model.loader;

import android.content.ContentResolver;
import android.net.Uri;

import com.qiugong.glide.core.key.ObjectKey;
import com.qiugong.glide.core.load.model.ModelLoaderRegistry;
import com.qiugong.glide.core.load.model.data.FileFetcher;
import com.qiugong.glide.core.load.model.data.LoadData;

import java.io.InputStream;

/**
 * @author qzx 20/2/23.
 */
public class UriFileLoader implements ModelLoader<Uri, InputStream> {

    private final ContentResolver contentResolver;

    private UriFileLoader(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public LoadData<InputStream> buildLoadData(Uri uri) {
        return new LoadData<>(new ObjectKey(uri), new FileFetcher(uri, contentResolver));
    }

    @Override
    public boolean handles(Uri uri) {
        return ContentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme());
    }

    public static class Factory implements ModelLoaderFactory<Uri, InputStream> {
        private final ContentResolver contentResolver;

        public Factory(ContentResolver contentResolver) {
            this.contentResolver = contentResolver;
        }

        @Override
        public ModelLoader<Uri, InputStream> build(ModelLoaderRegistry registry) {
            return new UriFileLoader(contentResolver);
        }

    }
}
