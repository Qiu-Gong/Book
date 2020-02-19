package com.qiugong.glide.core;

import android.content.Context;

import com.qiugong.glide.core.lifecycle.RequestManagerRetriever;

/**
 * @author qzx 20/2/19.
 */
class GlideBuilder {

    private RequestManagerRetriever requestManagerRetriever;

    Glide build(Context context) {
        context = context.getApplicationContext();
        requestManagerRetriever = new RequestManagerRetriever();

        return new Glide(context, this);
    }

    RequestManagerRetriever getRequestManagerRetriever() {
        return requestManagerRetriever;
    }
}
