package com.qiugong.glide.core.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;

/**
 * @author qzx 20/2/19.
 */
public class RequestManagerRetriever {

    private static final String FRAGMENT_TAG = "com.qiugong.glide.manger";

    private volatile RequestManager applicationManager;

    private RequestManager newManager(Context context, Lifecycle lifecycle) {
        return new RequestManager(context, lifecycle);
    }

    public RequestManager get(Context context) {
        if (context instanceof Application) {
            return getApplicationManager(context);
        } else if (context instanceof androidx.fragment.app.FragmentActivity) {
            return get((androidx.fragment.app.FragmentActivity) context);
        } else if (context instanceof Activity) {
            return get((Activity) context);
        }

        throw new RuntimeException("context instanceof error");
    }

    public RequestManager get(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        return fragmentGet(fm, activity);
    }

    public RequestManager get(androidx.fragment.app.FragmentActivity activity) {
        androidx.fragment.app.FragmentManager fm = activity.getSupportFragmentManager();
        return supportFragmentGet(fm, activity);
    }

    public RequestManager get(Fragment fragment) {
        FragmentManager fm = fragment.getChildFragmentManager();
        return fragmentGet(fm, fragment.getActivity());
    }

    public RequestManager get(androidx.fragment.app.Fragment fragment) {
        androidx.fragment.app.FragmentManager fm = fragment.getChildFragmentManager();
        return supportFragmentGet(fm, fragment.getActivity());
    }

    private RequestManager getApplicationManager(Context context) {
        if (applicationManager == null) {
            synchronized (this) {
                if (applicationManager == null) {
                    applicationManager = newManager(context.getApplicationContext(),
                            new ApplicationLifecycle());
                }
            }
        }
        return applicationManager;
    }

    private RequestManager supportFragmentGet(androidx.fragment.app.FragmentManager fm,
                                              androidx.fragment.app.FragmentActivity activity) {
        IRequestManagerFragment requestManager = getRequestManagerSupportFragment(fm);
        RequestManager manager = requestManager.getRequestManager();
        if (manager == null) {
            manager = newManager(activity, requestManager.getGlideLifecycle());
            requestManager.setRequestManager(manager);
        }
        return manager;
    }

    private RequestManager fragmentGet(FragmentManager fm, Activity activity) {
        IRequestManagerFragment requestManager = getRequestManagerFragment(fm);
        RequestManager manager = requestManager.getRequestManager();
        if (manager == null) {
            manager = newManager(activity, requestManager.getGlideLifecycle());
            requestManager.setRequestManager(manager);
        }
        return manager;
    }

    private RequestManagerSupportFragment getRequestManagerSupportFragment(androidx.fragment.app.FragmentManager fm) {
        RequestManagerSupportFragment fragment = (RequestManagerSupportFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new RequestManagerSupportFragment();
            fm.beginTransaction().add(fragment, FRAGMENT_TAG).commitAllowingStateLoss();
        }
        return fragment;
    }

    private RequestManagerFragment getRequestManagerFragment(FragmentManager fm) {
        RequestManagerFragment fragment = (RequestManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new RequestManagerFragment();
            fm.beginTransaction().add(fragment, FRAGMENT_TAG).commitAllowingStateLoss();
        }
        return fragment;
    }
}
