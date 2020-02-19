package com.qiugong.glide.core.lifecycle;

/**
 * @author qzx 20/2/19.
 */
interface IRequestManagerFragment {

    void setRequestManager(RequestManager requestManager);

    RequestManager getRequestManager();

    ActivityFragmentLifecycle getGlideLifecycle();
}
