package com.qiugong.glide.core.request;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import com.qiugong.glide.core.Glide;
import com.qiugong.glide.core.load.Engine;
import com.qiugong.glide.core.memory.active.Resource;

/**
 * @author qzx 20/2/20.
 */
public class Request implements ResourceCallback, Target.SizeReadyCallback {

    private enum Status {
        // 未开始
        PENDING,
        // 等待尺寸
        WAITING_FOR_SIZE,
        // 运行
        RUNNING,
        // 完成
        COMPLETE,

        // 暂停
        PAUSED,
        // 失败
        FAILED,
        // 取消
        CANCELLED,
        // 清除
        CLEARED,
    }

    private static final String TAG = "Request";

    private Context context;
    private Object model;
    private RequestOptions requestOptions;
    private Status status;
    private Resource resource;
    private Target target;

    private Drawable errorDrawable;
    private Drawable placeHolderDrawable;
    private Engine.LoadStatus loadStatus;

    Request(Context context, Object model, RequestOptions requestOptions, Target target) {
        this.context = context;
        this.model = model;
        this.requestOptions = requestOptions;
        this.target = target;
        this.status = Status.PENDING;
    }

    public void begin() {
        Log.d(TAG, "begin: context = " + context);
        status = Status.WAITING_FOR_SIZE;
        target.onLoadStarted(getPlaceHolderDrawable());
        if (requestOptions.getOverrideHeight() > 0 && requestOptions.getOverrideHeight() > 0) {
            // 自定义数值
            onSizeReady(requestOptions.getOverrideWidth(), requestOptions.getOverrideHeight());
        } else {
            // 计算尺寸
            target.startViewObserver(this);
        }
    }

    public void pause() {
        Log.d(TAG, "pause = " + context);
        status = Status.PAUSED;
        cancel();
    }

    public void cancel() {
        Log.d(TAG, "cancel = " + context);
        status = Status.CANCELLED;
        target.cancelViewObserver();
        if (loadStatus != null) {
            loadStatus.cancel();
            loadStatus = null;
        }
    }

    public void clear() {
        Log.d(TAG, "clear = " + context);
        if (status == Status.CLEARED) {
            return;
        }

        if (resource != null) {
            resource.release();
        }
        status = Status.CLEARED;
    }

    public void recycle() {
        Log.d(TAG, "recycle = " + context);
        this.context = null;
        this.model = null;
        this.requestOptions = null;
        this.target = null;
        this.status = null;

        this.errorDrawable = null;
        this.placeHolderDrawable = null;
        this.loadStatus = null;
    }

    public boolean isRunning() {
        return status == Status.RUNNING || status == Status.WAITING_FOR_SIZE;
    }

    public boolean isComplete() {
        return status == Status.COMPLETE;
    }

    public boolean isCancelled() {
        return status == Status.CANCELLED || status == Status.CLEARED;
    }

    public boolean isFailed() {
        return status == Status.FAILED;
    }

    @Override
    public void onResourceReady(Resource resource) {
        Log.d(TAG, "onResourceReady: content = " + context + " resource = " + resource);
        this.loadStatus = null;
        this.resource = resource;
        if (this.resource == null) {
            status = Status.FAILED;
            setErrorPlaceHolder();
        } else {
            target.onResourceReady(resource.getBitmap());
        }
    }

    @Override
    public void onSizeReady(int width, int height) {
        Log.d(TAG, "onSizeReady: content = " + context + " width = " + width + " height = " + height);
        if (status != Status.WAITING_FOR_SIZE) {
            return;
        }

        status = Status.RUNNING;
        loadStatus = Glide.get(context)
                .getEngine()
                .load(model, width, height, this);
    }

    private void setErrorPlaceHolder() {
        Drawable error = getErrorDrawable();
        if (error == null) {
            error = getPlaceHolderDrawable();
        }
        target.onLoadFailed(error);
    }

    private Drawable getPlaceHolderDrawable() {
        if (placeHolderDrawable == null && requestOptions.getPlaceholderId() > 0) {
            placeHolderDrawable = loadDrawable(requestOptions.getPlaceholderId());
        }
        return placeHolderDrawable;
    }

    private Drawable getErrorDrawable() {
        if (errorDrawable == null && requestOptions.getErrorId() > 0) {
            errorDrawable = loadDrawable(requestOptions.getErrorId());
        }
        return errorDrawable;
    }

    private Drawable loadDrawable(int resourceId) {
        return ResourcesCompat.getDrawable(context.getResources(), resourceId, context.getTheme());
    }
}
