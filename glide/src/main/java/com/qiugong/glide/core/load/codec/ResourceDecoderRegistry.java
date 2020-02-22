package com.qiugong.glide.core.load.codec;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qzx 20/2/23.
 */
public class ResourceDecoderRegistry {

    private static class Entry<T> {
        private final Class<T> dataClass;
        private final ResourceDecoder<T> decoder;

        Entry(Class<T> dataClass, ResourceDecoder<T> decoder) {
            this.dataClass = dataClass;
            this.decoder = decoder;
        }

        boolean handles(Class<?> dataClass) {
            return this.dataClass.isAssignableFrom(dataClass);
        }
    }

    private final List<Entry<?>> entries = new ArrayList<>();

    public <T> void add(Class<T> cls, ResourceDecoder<T> decoder) {
        entries.add(new Entry<>(cls, decoder));
    }

    public <Data> List<ResourceDecoder<Data>> getDecoders(Class<?> cls) {
        List<ResourceDecoder<Data>> decoders = new ArrayList<>();
        for (Entry<?> entry : entries) {
            if (entry.handles(cls)) {
                decoders.add((ResourceDecoder<Data>) entry.decoder);
            }
        }
        return decoders;
    }
}
