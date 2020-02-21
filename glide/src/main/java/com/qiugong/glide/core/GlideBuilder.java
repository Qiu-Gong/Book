package com.qiugong.glide.core;

import android.content.Context;

import com.qiugong.glide.core.lifecycle.RequestManagerRetriever;
import com.qiugong.glide.core.load.Engine;
import com.qiugong.glide.core.request.RequestOptions;

/**
 * @author qzx 20/2/19.
 */
class GlideBuilder {

    private RequestManagerRetriever requestManagerRetriever;
    private RequestOptions requestOptions;
    private Engine engine;

    Glide build(Context context) {
        context = context.getApplicationContext();
        requestManagerRetriever = new RequestManagerRetriever();
        requestOptions = new RequestOptions();

        if(engine == null){
            engine = new Engine(context);
        }

        return new Glide(context, this);
    }

    RequestManagerRetriever getRequestManagerRetriever() {
        return requestManagerRetriever;
    }

    public RequestOptions getRequestOptions() {
        return requestOptions;
    }

    public Engine getEngine() {
        return engine;
    }
}
