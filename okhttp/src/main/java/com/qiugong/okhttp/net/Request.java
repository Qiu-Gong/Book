package com.qiugong.okhttp.net;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qzx 20/2/18.
 */
public class Request {

    private HttpUrl url;
    private Map<String, String> headers;
    private String method;
    private RequestBody body;

    private Request(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    String method() {
        return method;
    }

    public HttpUrl url() {
        return url;
    }

    public RequestBody body() {
        return body;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public final static class Builder {
        private HttpUrl url;
        private Map<String, String> headers = new HashMap<>();
        private String method;
        private RequestBody body;

        public Builder url(String url) {
            this.url = new HttpUrl(url);
            return this;
        }

        public Builder addHeader(String name, String value) {
            this.headers.put(name, value);
            return this;
        }

        public Builder removeHeader(String name) {
            this.headers.remove(name);
            return this;
        }

        public Builder get() {
            this.method = "GET";
            return this;
        }

        public Builder post(RequestBody body) {
            this.body = body;
            this.method = "POST";
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
