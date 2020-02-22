package com.qiugong.glide.core.load.model;

import com.qiugong.glide.core.load.codec.ResourceDecoder;

import java.util.List;

/**
 * @author qzx 20/2/23.
 */
public class ResourceDecoderRegistry {

    public <T> void add(Class<T> cls, ResourceDecoder<T> decoder) {
    }

    public <Data> List<ResourceDecoder<Data>> getDecoders(Class<?> cls) {
        return null;
    }
}
