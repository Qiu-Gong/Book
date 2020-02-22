package com.qiugong.glide.core.load;

import android.content.Context;
import android.util.Log;

import com.qiugong.glide.core.Glide;
import com.qiugong.glide.core.key.EngineKey;
import com.qiugong.glide.core.key.Key;
import com.qiugong.glide.core.memory.active.ActiveResources;
import com.qiugong.glide.core.memory.active.Resource;
import com.qiugong.glide.core.memory.cache.ResourceCache;
import com.qiugong.glide.core.request.ResourceCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qzx 20/2/21.
 */
public class Engine implements
        ResourceCache.ResourceRemovedListener,
        Resource.ResourceListener,
        EngineJobListener {

    public static class LoadStatus {

        private final EngineJob engineJob;
        private final ResourceCallback resourceCallback;

        LoadStatus(EngineJob job, ResourceCallback callback) {
            engineJob = job;
            resourceCallback = callback;
        }

        public void cancel() {
            engineJob.removeCallback(resourceCallback);
        }
    }

    private static final String TAG = "Engine";

    private Context context;
    private ActiveResources activeResources;
    private Map<Key, EngineJob> engineJobs = new HashMap<>();

    public Engine(Context context) {
        this.context = context;
        this.activeResources = new ActiveResources(this);
    }

    public LoadStatus load(Object model, int width, int height, ResourceCallback callback) {
        EngineKey engineKey = new EngineKey(model, width, height);
        Resource resource;
        EngineJob engineJob;

        resource = activeResources.get(engineKey);
        if (resource != null) {
            Log.d(TAG, "load: 使用 活动缓存");
            resource.acquire();
            callback.onResourceReady(resource);
            return null;
        }

        resource = Glide.get(context).getMemoryCache().remove(engineKey);
        if (null != resource) {
            Log.d(TAG, "load: 使用 内存缓存，移出 存缓存 加入 活跃缓存");
            activeResources.activate(engineKey, resource);
            resource.acquire();
            resource.setResourceListener(engineKey, this);
            callback.onResourceReady(resource);
            return null;
        }

        // 重复请求
        engineJob = engineJobs.get(engineKey);
        if (engineJob != null) {
            Log.d(TAG, "load: 重复加载");
            engineJob.addCallback(callback);
            return new LoadStatus(engineJob, callback);
        }

        // 新建任务
        Log.d(TAG, "load: 新建任务");
        engineJob = new EngineJob(context, engineKey, this);
        engineJob.addCallback(callback);
        DecodeJob decodeJob = new DecodeJob(context, model, width, height, engineJob);
        engineJob.start(decodeJob);
        engineJobs.put(engineKey, engineJob);
        return new LoadStatus(engineJob, callback);
    }

    @Override
    public void onResourceReleased(Key key, Resource resource) {
        Log.d(TAG, "onResourceReleased: 移出 活动缓存，加入 内存缓存 " + key);
        activeResources.deactivate(key);
        Glide.get(context).getMemoryCache().put(key, resource);
    }

    @Override
    public void onResourceRemoved(Resource resource) {
        Log.d(TAG, "onResourceRemoved: 移出 内存缓存，加入 复用池");
        Glide.get(context).getBitmapPool().put(resource.getBitmap());
    }

    @Override
    public void onEngineJobComplete(EngineJob engineJob, Key key, Resource resource) {
        if (resource != null) {
            Log.d(TAG, "onEngineJobComplete: 加入 活动缓存 " + key);
            resource.setResourceListener(key, this);
            activeResources.activate(key, resource);
        }
        engineJobs.remove(key);
    }

    @Override
    public void onEngineJobCancelled(EngineJob engineJob, Key key) {
        engineJobs.remove(key);
    }
}
