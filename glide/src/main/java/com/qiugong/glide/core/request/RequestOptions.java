package com.qiugong.glide.core.request;

/**
 * @author qzx 20/2/21.
 */
public class RequestOptions {

    private int errorId;
    private int placeholderId;
    private int overrideHeight = -1;
    private int overrideWidth = -1;

    public RequestOptions plcaeHolder(int resourceId) {
        this.placeholderId = resourceId;
        return this;
    }

    public RequestOptions error(int resourceId) {
        this.errorId = resourceId;
        return this;
    }

    public RequestOptions override(int width, int height) {
        this.overrideWidth = width;
        this.overrideHeight = height;
        return this;
    }

    int getErrorId() {
        return errorId;
    }

    int getPlaceholderId() {
        return placeholderId;
    }

    int getOverrideHeight() {
        return overrideHeight;
    }

    int getOverrideWidth() {
        return overrideWidth;
    }
}
