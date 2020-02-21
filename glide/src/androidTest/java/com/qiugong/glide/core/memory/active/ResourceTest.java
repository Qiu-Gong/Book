package com.qiugong.glide.core.memory.active;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.qiugong.glide.R;
import com.qiugong.glide.core.key.Key;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.MessageDigest;

/**
 * @author qzx 20/2/20.
 */
@RunWith(AndroidJUnit4.class)
public class ResourceTest {
    @Test
    public void testResource() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Bitmap bitmap = BitmapFactory.decodeResource(appContext.getResources(), R.mipmap.ic_launcher);

        Resource resource = new Resource(bitmap);
        resource.setResourceListener(new Key() {
            @Override
            public void updateDiskCacheKey(MessageDigest messageDigest) {

            }

            @Override
            public byte[] getKeyBytes() {
                return new byte[0];
            }
        }, new Resource.ResourceListener() {
            @Override
            public void onResourceReleased(Key key, Resource resource) {
                resource.recycle();
            }
        });

        resource.acquire();
        resource.acquire();
        resource.release();
        resource.release();
    }
}
