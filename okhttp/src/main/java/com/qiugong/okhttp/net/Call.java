package com.qiugong.okhttp.net;

import android.text.TextUtils;

import com.qiugong.okhttp.net.chain.CallServiceInterceptor;
import com.qiugong.okhttp.net.chain.ConnectionInterceptor;
import com.qiugong.okhttp.net.chain.HeadersInterceptor;
import com.qiugong.okhttp.net.chain.Interceptor;
import com.qiugong.okhttp.net.chain.InterceptorChain;
import com.qiugong.okhttp.net.chain.RetryInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qzx 20/2/18.
 */
public class Call {

    private HttpClient httpClient;
    private Request request;

    private boolean executed;
    private boolean canceled;

    Call(HttpClient httpClient, Request request) {
        this.httpClient = httpClient;
        this.request = request;
    }

    public void enqueue(Callback callback) {
        synchronized (this) {
            if (executed) throw new IllegalStateException("already executed");
            executed = true;
        }
        httpClient.dispatcher().enqueue(new AsyncCall(callback));
    }

    public void cancel() {
        this.canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }

    private Response getResponse() throws IOException {
        //用户添加的全局拦截器
        List<Interceptor> interceptors = new ArrayList<>(httpClient.interceptors());
        // 错误拦截器
        interceptors.add(new RetryInterceptor());
        // 添加头
        interceptors.add(new HeadersInterceptor());
        // 连接拦截器
        interceptors.add(new ConnectionInterceptor());
        // 真正访问服务器的拦截器
        interceptors.add(new CallServiceInterceptor());
        InterceptorChain chain = new InterceptorChain(interceptors, 0, this, null);
        return chain.proceed();
    }

    class AsyncCall implements Runnable {

        private final Callback callback;

        AsyncCall(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            try {
                // 开始执行链式
                Response response = getResponse();
                if (canceled) {
                    callback.onFailure(Call.this, new IOException("Canceled"));
                } else {
                    callback.onResponse(Call.this, response);
                }
            } catch (IOException e) {
                callback.onFailure(Call.this, e);
            } finally {
                httpClient.dispatcher().finished(this);
            }
        }

        @Override
        public String toString() {
            return "AsyncCall{" +
                    "host=" + request.url().getHost() +
                    " port=" + request.url().getPort() +
                    '}';
        }

        String getHost() {
            return request.url().getHost();
        }

        int getPort() {
            return request.url().getPort();
        }

        boolean isSameAddress(AsyncCall asyncCall) {
            return TextUtils.equals(getHost(), asyncCall.getHost()) &&
                    getPort() == asyncCall.getPort();
        }
    }

    public Request getRequest() {
        return request;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }
}
