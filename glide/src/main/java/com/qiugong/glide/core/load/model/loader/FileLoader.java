package com.qiugong.glide.core.load.model.loader;

import android.net.Uri;

import com.qiugong.glide.core.load.model.ModelLoaderRegistry;
import com.qiugong.glide.core.load.model.data.LoadData;

import java.io.File;
import java.io.InputStream;

/**
 * @author qzx 20/2/23.
 */
public class FileLoader<Data> implements ModelLoader<File, Data> {

    private final ModelLoader<Uri, Data> loader;

    private FileLoader(ModelLoader<Uri, Data> loader) {
        this.loader = loader;
    }

    @Override
    public LoadData<Data> buildLoadData(File file) {
        return loader.buildLoadData(Uri.fromFile(file));
    }

    @Override
    public boolean handles(File file) {
        return true;
    }

    public static class Factory implements ModelLoaderFactory<File, InputStream> {
        @Override
        public ModelLoader<File, InputStream> build(ModelLoaderRegistry registry) {
            return new FileLoader<>(registry.build(Uri.class, InputStream.class));
        }
    }
}
