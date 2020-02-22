package com.qiugong.glide.core.load.codec;

import android.graphics.Bitmap;

import java.util.List;

/**
 * @author qzx 20/2/22.
 */
public class LoadPath<Data> {
    public LoadPath(Class<?> cls, List<ResourceDecoder<Data>> decoders) {

    }

    public Bitmap runLoad(Data data, int width, int height) {
        return null;
    }
}
