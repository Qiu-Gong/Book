package com.qiugong.glide.core.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * @author qzx 20/2/20.
 */
public class Target {

    private static final String TAG = "Target";

    private static int maxDisplayLength = -1;
    private ImageView view;

    private SizeReadyCallback sizeReadyCallback;
    private LayoutListener layoutListener;

    public interface SizeReadyCallback {
        void onSizeReady(int width, int height);
    }

    private static final class LayoutListener implements
            ViewTreeObserver.OnPreDrawListener {

        private final WeakReference<Target> targetRef;

        LayoutListener(Target target) {
            this.targetRef = new WeakReference<>(target);
        }

        @Override
        public boolean onPreDraw() {
            Target target = targetRef.get();
            if (target != null) {
                target.checkDimens();
            }
            return true;
        }
    }

    public Target(ImageView view) {
        this.view = view;
    }

    public void onLoadFailed(Drawable drawable) {
        view.setImageDrawable(drawable);
    }

    public void onLoadStarted(Drawable placeHolderDrawable) {
        view.setImageDrawable(placeHolderDrawable);
    }

    public void onResourceReady(Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    public void startViewObserver(SizeReadyCallback callback) {
        Log.d(TAG, "startViewObserver: " + view);
        int currentWidth = getTargetWidth();
        int currentHeight = getTargetHeight();
        if (currentHeight > 0 && currentWidth > 0) {
            callback.onSizeReady(currentWidth, currentHeight);
            return;
        }

        this.sizeReadyCallback = callback;
        if (layoutListener == null) {
            ViewTreeObserver observer = view.getViewTreeObserver();
            layoutListener = new LayoutListener(this);
            observer.addOnPreDrawListener(layoutListener);
        }
    }

    public void cancelViewObserver() {
        Log.d(TAG, "cancelViewObserver: " + view);
        ViewTreeObserver observer = view.getViewTreeObserver();
        if (observer.isAlive()) {
            observer.removeOnPreDrawListener(layoutListener);
        }
        layoutListener = null;
        sizeReadyCallback = null;
    }

    private void checkDimens() {
        if (sizeReadyCallback == null) {
            return;
        }

        int width = getTargetWidth();
        int height = getTargetHeight();
        if (width <= 0 && height <= 0) {
            return;
        }

        Log.d(TAG, "checkDimens: width = " + width + " height = " + height);
        sizeReadyCallback.onSizeReady(width, height);
        cancelViewObserver();
    }

    private int getTargetHeight() {
        int verticalPadding = view.getPaddingTop() + view.getPaddingBottom();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int layoutParamSize = layoutParams != null ? layoutParams.height : 0;
        return getTargetDimen(view.getHeight(), layoutParamSize, verticalPadding);
    }

    private int getTargetWidth() {
        int horizontalPadding = view.getPaddingLeft() + view.getPaddingRight();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int layoutParamSize = layoutParams != null ? layoutParams.width : 0;
        return getTargetDimen(view.getWidth(), layoutParamSize, horizontalPadding);
    }

    private int getTargetDimen(int viewSize, int layoutParamSize, int paddingSize) {
        int adjustedParamSize = layoutParamSize - paddingSize;
        if (adjustedParamSize > 0) {
            return adjustedParamSize;
        }

        int adjustedViewSize = viewSize - paddingSize;
        if (adjustedViewSize > 0) {
            return adjustedViewSize;
        }

        if (!view.isLayoutRequested() && layoutParamSize == ViewGroup.LayoutParams.WRAP_CONTENT) {
            return getMaxDisplayLength(view.getContext());
        }

        return 0;
    }

    private int getMaxDisplayLength(Context context) {
        if (maxDisplayLength == -1) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            maxDisplayLength = Math.max(point.x, point.y);
        }
        return maxDisplayLength;
    }
}
