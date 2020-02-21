package com.qiugong.glide.core.load;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.qiugong.glide.R;
import com.qiugong.glide.core.memory.active.Resource;
import com.qiugong.glide.core.request.ResourceCallback;

/**
 * @author qzx 20/2/21.
 */
public class Engine {

    private Context context;

    public Engine(Context context) {
        this.context = context;
    }

    public LoadStatus load(Object model, int width, int height, ResourceCallback callback) {
        Bitmap bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.mipmap.ic_launcher);
        Resource resource = new Resource(bitmap);
        callback.onResourceReady(resource);
        return new LoadStatus();
    }

    public static class LoadStatus {

        public void cancel() {

        }
    }
}
