package com.qiugong.glide.core.lifecycle;

import com.qiugong.glide.core.request.Request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * @author qzx 20/2/19.
 */
class RequestTracker {

    private final Set<Request> requests
            = Collections.newSetFromMap(new WeakHashMap<Request, Boolean>());
    private boolean isPaused;

    void runRequest(Request request) {
        requests.add(request);
        if (!isPaused) {
            request.begin();
        }
    }

    void resumeRequests() {
        isPaused = false;
        for (Request request : getSnapshot(requests)) {
            if (!request.isComplete() && !request.isCancelled() && !request.isRunning()) {
                request.begin();
            }
        }
    }

    void pauseRequests() {
        isPaused = true;
        for (Request request : getSnapshot(requests)) {
            if (request.isRunning()) {
                request.pause();
            }
        }
    }

    void clearRequests() {
        for (Request request : getSnapshot(requests)) {
            if (request == null) {
                return;
            }
            request.clear();
            request.recycle();
            requests.remove(request);
        }
    }

    private static <T> List<T> getSnapshot(Collection<T> other) {
        List<T> result = new ArrayList<>(other.size());
        for (T item : other) {
            if (item != null) {
                result.add(item);
            }
        }
        return result;
    }
}
