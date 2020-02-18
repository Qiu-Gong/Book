package com.qiugong.okhttp.net.chain;

import android.util.Log;

import com.qiugong.okhttp.net.HttpCodec;
import com.qiugong.okhttp.net.Request;
import com.qiugong.okhttp.net.RequestBody;
import com.qiugong.okhttp.net.Response;

import java.io.IOException;
import java.util.Map;

/**
 * @author qzx 20/2/18.
 */
public class HeadersInterceptor implements Interceptor {

    private static final String TAG = "HeadersInterceptor";

    @Override
    public Response intercept(InterceptorChain chain) throws IOException {
        Log.d(TAG, "HeadersInterceptor intercept...");

        Request request = chain.getCall().getRequest();
        Map<String, String> headers = request.headers();
        headers.put(HttpCodec.HEAD_HOST, request.url().getHost());
        headers.put(HttpCodec.HEAD_CONNECTION, HttpCodec.HEAD_VALUE_KEEP_ALIVE);

        if (request.body() != null) {
            headers.put(HttpCodec.HEAD_CONTENT_TYPE, RequestBody.CONTENT_TYPE);
            int length = request.body().body().length();
            if (length != 0) {
                headers.put(HttpCodec.HEAD_CONTENT_LENGTH, Long.toString(length));
            }
        }

        return chain.proceed();
    }
}
