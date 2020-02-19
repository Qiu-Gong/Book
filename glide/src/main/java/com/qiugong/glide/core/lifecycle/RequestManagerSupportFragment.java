package com.qiugong.glide.core.lifecycle;

import android.util.Log;

import androidx.fragment.app.Fragment;

/**
 * @author qzx 20/2/19.
 */
public class RequestManagerSupportFragment extends Fragment implements IRequestManagerFragment {

    private static final String TAG = "RequestManagerSupportFr";

    private final ActivityFragmentLifecycle lifecycle;
    private RequestManager requestManager;

    public RequestManagerSupportFragment() {
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
