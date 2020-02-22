package com.qiugong.glide.core.load.codec;

import android.graphics.Bitmap;

import java.io.IOException;
import java.util.List;

/**
 * @author qzx 20/2/22.
 */
public class LoadPath<Data> {

    private final List<ResourceDecoder<Data>> decoders;

    public LoadPath(List<ResourceDecoder<Data>> decoders) {
        this.decoders = decoders;
    }

    /**
     * 解码
     */
    public Bitmap runLoad(Data data, int width, int height) {
        Bitmap result = null;
        for (ResourceDecoder<Data> decoder : decoders) {
            try {
                if (decoder.handles(data)) {
                    result = decoder.decode(data, width, height);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (result != null) {
                break;
            }
        }
        return result;
    }
}
