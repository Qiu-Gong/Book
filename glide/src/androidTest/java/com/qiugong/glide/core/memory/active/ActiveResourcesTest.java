package com.qiugong.glide.core.memory.active;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.qiugong.glide.R;
import com.qiugong.glide.core.memory.Key;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.MessageDigest;

/**
 * @author qzx 20/2/20.
 */
@RunWith(AndroidJUnit4.class)
public class ActiveResourcesTest {

    class Active {
        Bitmap bitmap;
        Resource resource;
        Key key;

        public Active(Resources resources, int id) {
            bitmap = BitmapFactory.decodeResource(resources, id);
            resource = new Resource(bitmap);
            key = new Key() {
                @Override
                public void updateDiskCacheKey(MessageDigest messageDigest) {

                }
            };
        }

        @NonNull
        @Override
        public String toString() {
            return "bitmap:" + bitmap + " resource:" + resource + " key:" + key;
        }
    }

    @Test
    private void testActiveResources() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Active a1 = new Active(appContext.getResources(), R.mipmap.ic_launcher);
        Active a2 = new Active(appContext.getResources(), R.mipmap.ic_launcher);
        Active a3 = new Active(appContext.getResources(), R.mipmap.ic_launcher);

        ActiveResources resources = new ActiveResources(new Resource.ResourceListener() {
            @Override
            public void onResourceReleased(Key key, Resource resource) {
                Log.d("TEST", "onResourceReleased key:" + key + " resource:" + resource);
            }
        });
        resources.activate(a1.key, a1.resource);
        resources.activate(a2.key, a2.resource);
        resources.activate(a3.key, a3.resource);

        Log.d("TEST", "a1:" + resources.get(a1.key)
                + " a2:" + resources.get(a2.key)
                + " a3:" + resources.get(a3.key));

        resources.deactivate(a1.key);
        resources.deactivate(a2.key);
        resources.deactivate(a3.key);

        System.gc();

        resources.shutDown();
    }
}
