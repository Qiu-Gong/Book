package com.qiugong.glide.core.memory.disk;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.qiugong.glide.core.key.Key;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * @author qzx 20/2/20.
 */
@RunWith(AndroidJUnit4.class)
public class DiskLruCacheWrapperTest {

    @Test
    public void testDiskLruCache() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Key key = new Key() {
            @Override
            public void updateDiskCacheKey(MessageDigest messageDigest) {
                messageDigest.update(toString().getBytes());
            }

            @Override
            public byte[] getKeyBytes() {
                return new byte[0];
            }
        };

        DiskLruCacheWrapper wrapper = new DiskLruCacheWrapper(appContext);
        wrapper.put(key, new DiskCache.Writer() {
            @Override
            public boolean write(File file) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write("1234567890");
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    return false;
                }
                return true;
            }
        });
    }
}