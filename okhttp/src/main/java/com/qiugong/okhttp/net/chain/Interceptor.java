package com.qiugong.okhttp.net.chain;

import com.qiugong.okhttp.net.Response;

import java.io.IOException;

/**
 * @author qzx 20/2/18.
 */
public interface Interceptor {
    Response intercept(InterceptorChain chain) throws IOException;
}
