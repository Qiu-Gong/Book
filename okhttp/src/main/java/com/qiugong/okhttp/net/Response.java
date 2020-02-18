package com.qiugong.okhttp.net;

import java.util.Map;

/**
 * @author qzx 20/2/18.
 */
public class Response {

    private int code;
    private int contentLength;

    private Map<String, String> headers;
    private String body;
    private boolean isKeepAlive;

    public Response(int code, int contentLength, Map<String, String> headers, String body, boolean isKeepAlive) {
        this.code = code;
        this.contentLength = contentLength;
        this.headers = headers;
        this.body = body;
        this.isKeepAlive = isKeepAlive;
    }

    public int getCode() {
        return code;
    }

    public int getContentLength() {
        return contentLength;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public boolean isKeepAlive() {
        return isKeepAlive;
    }
}
