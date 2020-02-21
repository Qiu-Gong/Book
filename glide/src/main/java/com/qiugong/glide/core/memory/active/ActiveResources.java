package com.qiugong.glide.core.memory.active;

import android.util.Log;

import com.qiugong.glide.core.key.Key;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * TODO 弱引用如何释放资源
 *
 * @author qzx 20/2/20.
 */
public class ActiveResources {

    private static final String TAG = "ActiveResources";

    private Resource.ResourceListener resourceListener;

    private Map<Key, ResourceWeakReference> activeResources = new WeakHashMap<>();
    private ReferenceQueue<Resource> referenceQueue;
    private Thread cleanReferenceQueueThread;
    private volatile boolean threadShutDown;

    public ActiveResources(Resource.ResourceListener listener) {
        this.resourceListener = listener;
    }

    public void activate(Key key, Resource resource) {
        Log.d(TAG, "activate key:" + key + " resource:" + resource);
        resource.setResourceListener(key, resourceListener);
        activeResources.put(key, new ResourceWeakReference(key, resource, getReferenceQueue()));
    }

    public void deactivate(Key key) {
        Log.d(TAG, "deactivate key:" + key);
        ResourceWeakReference resource = activeResources.remove(key);
        if (resource != null) {
            resource.clear();
        }
    }

    public Resource get(Key key) {
        Log.d(TAG, "get key:" + key);
        ResourceWeakReference resource = activeResources.get(key);
        if (resource != null) {
            return resource.get();
        }
        return null;
    }

    public void shutDown() {
        Log.d(TAG, "shutDown");
        threadShutDown = true;
        if (cleanReferenceQueueThread == null) {
            return;
        }

        try {
            cleanReferenceQueueThread.interrupt();
            cleanReferenceQueueThread.join(5);
            if (cleanReferenceQueueThread.isAlive()) {
                throw new RuntimeException("clean Reference Queue Thread failed shut down.");
            }
        } catch (InterruptedException e) {
        }
    }

    private ReferenceQueue<? super Resource> getReferenceQueue() {
        if (referenceQueue == null) {
            referenceQueue = new ReferenceQueue<>();
            cleanReferenceQueueThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run...");
                    while (!threadShutDown) {
                        try {
                            ResourceWeakReference resource = (ResourceWeakReference) referenceQueue.remove();
                            Log.d(TAG, "remove active resources. key:" + resource.key);
                            activeResources.remove(resource.key);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, "active-resources");
            cleanReferenceQueueThread.start();
        }
        return referenceQueue;
    }

    private static class ResourceWeakReference extends WeakReference<Resource> {

        private Key key;

        public ResourceWeakReference(Key key, Resource resource,
                                     ReferenceQueue<? super Resource> queue) {
            super(resource, queue);
        }
    }
}
