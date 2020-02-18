package com.qiugong.okhttp.net.chain;

import com.qiugong.okhttp.net.Call;
import com.qiugong.okhttp.net.HttpConnection;
import com.qiugong.okhttp.net.Response;

import java.io.IOException;
import java.util.List;

/**
 * @author qzx 20/2/18.
 */
public class InterceptorChain {

    private final int index;
    private final Call call;
    private final HttpConnection connection;
    private final List<Interceptor> interceptors;

    public InterceptorChain(List<Interceptor> interceptors, int index, Call call, HttpConnection connection) {
        this.index = index;
        this.call = call;
        this.connection = connection;
        this.interceptors = interceptors;
    }

    public Response proceed() throws IOException {
        return proceed(connection);
    }

    Response proceed(HttpConnection connection) throws IOException {
        InterceptorChain next = new InterceptorChain(interceptors, index + 1, call, connection);
        Interceptor interceptor = interceptors.get(index);
        return interceptor.intercept(next);
    }

    Call getCall() {
        return call;
    }

    HttpConnection getConnection() {
        return connection;
    }
}
