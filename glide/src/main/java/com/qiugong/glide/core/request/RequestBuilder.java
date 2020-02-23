package com.qiugong.glide.core.request;

import android.content.Context;
import android.widget.ImageView;

import com.qiugong.glide.core.Glide;
import com.qiugong.glide.core.lifecycle.RequestManager;

import java.io.File;

/**
 * @author qzx 20/2/20.
 */
public class RequestBuilder implements ModelTypes {

    private Context context;
    private RequestManager requestManager;
    private RequestOptions requestOptions;
    private Object model;

    public RequestBuilder(Context context, RequestManager requestManager) {
        this.context = context;
        this.requestManager = requestManager;
        this.requestOptions = Glide.get(context).getRequestOptions();
    }

    public RequestBuilder apply(RequestOptions options) {
        this.requestOptions = options;
        return this;
    }

    @Override
    public RequestBuilder load(String path) {
        model = path;
        return this;
    }

    @Override
    public RequestBuilder load(File path) {
        model = path;
        return this;
    }

    public Target into(ImageView view) {
        Target target = new Target(view);
        requestManager.track(new Request(context, model, requestOptions, target));
        return target;
    }
}
