package com.qiugong.glide.core.lifecycle;

import android.app.Fragment;
import android.util.Log;

/**
 * @author qzx 20/2/19.
 */
public class RequestManagerFragment extends Fragment implements IRequestManagerFragment {

    private static final String TAG = "RequestManagerFragment";

    private final ActivityFragmentLifecycle lifecycle;
    private RequestManager requestManager;

    public RequestManagerFragment() {
        this.lifecycle = new ActivityFragmentLifecycle();
    }

    @Override
    public void setRequestManager(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @Override
    public RequestManager getRequestManager() {
        return this.requestManager;
    }

    @Override
    public ActivityFragmentLifecycle getGlideLifecycle() {
        return lifecycle;
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart:" + requestManager.getContext());
        super.onStart();
        lifecycle.onStart();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop:" + requestManager.getContext());
        super.onStop();
        lifecycle.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy:" + requestManager.getContext());
        super.onDestroy();
        lifecycle.onDestroy();
    }
}
