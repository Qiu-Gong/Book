package com.qiugong.glide.core.load.model.loader;

import android.net.Uri;

import com.qiugong.glide.core.load.model.ModelLoaderRegistry;
import com.qiugong.glide.core.load.model.data.LoadData;

import java.io.File;
import java.io.InputStream;

/**
 * @author qzx 20/2/23.
 */
public class StringLoader<Data> implements ModelLoader<String, Data> {

    private final ModelLoader<Uri, Data> uriLoader;

    private StringLoader(ModelLoader<Uri, Data> uriLoader) {
        this.uriLoader = uriLoader;
    }

    @Override
    public LoadData<Data> buildLoadData(String model) {
        Uri uri;
        if (model.startsWith("/")) {
            uri = Uri.fromFile(new File(model));
        } else {
            uri = Uri.parse(model);
        }
        return uriLoader.buildLoadData(uri);
    }

    @Override
    public boolean handles(String string) {
        return true;
    }

    public static class Factory implements ModelLoaderFactory<String, InputStream> {

        /**
         * 将String 交给 Uri 的组件处理
         */
        @Override
        public ModelLoader<String, InputStream> build(ModelLoaderRegistry registry) {
            return new StringLoader<>(registry.build(Uri.class, InputStream.class));
        }
    }
}
