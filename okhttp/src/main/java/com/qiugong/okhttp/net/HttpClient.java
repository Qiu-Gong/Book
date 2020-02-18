package com.qiugong.okhttp.net;

import com.qiugong.okhttp.net.chain.Interceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qzx 20/2/18.
 */
public class HttpClient {

    private Dispatcher dispatcher;
    private ConnectionPool connectionPool;
    private List<Interceptor> interceptors;
    private int retry;

    public HttpClient() {
        this(new Builder());
    }

    private HttpClient(Builder builder) {
        dispatcher = builder.dispatcher;
        connectionPool = builder.connectionPool;
        interceptors = builder.interceptors;
        retry = builder.retry;
    }

    Dispatcher dispatcher() {
        return dispatcher;
    }

    public ConnectionPool connectionPool() {
        return connectionPool;
    }

    List<Interceptor> interceptors() {
        return interceptors;
    }

    public int retry() {
        return retry;
    }

    public Call call(Request request) {
        return new Call(this, request);
    }

    public static final class Builder {
        private Dispatcher dispatcher = new Dispatcher();
        private ConnectionPool connectionPool = new ConnectionPool();
        private List<Interceptor> interceptors = new ArrayList<>();
        private int retry = 3;

        public Builder retry(int retry) {
            this.retry = retry;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            this.interceptors.add(interceptor);
            return this;
        }
    }
}
