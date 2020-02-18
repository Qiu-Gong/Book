package com.qiugong.okhttp.net.chain;

import android.util.Log;

import com.qiugong.okhttp.net.Call;
import com.qiugong.okhttp.net.Response;

import java.io.IOException;

/**
 * @author qzx 20/2/18.
 */
public class RetryInterceptor implements Interceptor {

    private static final String TAG = "RetryInterceptor";

    @Override
    public Response intercept(InterceptorChain chain) throws IOException {
        Log.d(TAG, "RetryInterceptor intercept...");

        Call call = chain.getCall();
        for (int i = 0; i < chain.getCall().getHttpClient().retry(); i++) {
            if (call.isCanceled()) {
                throw new IOException("Canceled...");
            }
            try {
                return chain.proceed();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
