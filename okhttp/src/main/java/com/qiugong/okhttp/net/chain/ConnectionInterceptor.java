package com.qiugong.okhttp.net.chain;

import android.util.Log;

import com.qiugong.okhttp.net.HttpClient;
import com.qiugong.okhttp.net.HttpConnection;
import com.qiugong.okhttp.net.HttpUrl;
import com.qiugong.okhttp.net.Request;
import com.qiugong.okhttp.net.Response;

import java.io.IOException;

/**
 * @author qzx 20/2/18.
 */
public class ConnectionInterceptor implements Interceptor {

    private static final String TAG = "ConnectionInterceptor";

    @Override
    public Response intercept(InterceptorChain chain) throws IOException {
        Log.d(TAG, "ConnectionInterceptor intercept...");

        Request request = chain.getCall().getRequest();
        HttpUrl url = request.url();
        String host = url.getHost();
        int port = url.getPort();

        HttpClient httpClient = chain.getCall().getHttpClient();
        HttpConnection connection = httpClient.connectionPool().get(host, port);
        if (connection == null) {
            connection = new HttpConnection();
        }
        connection.setRequest(request);

        Response response = chain.proceed(connection);
        if (response.isKeepAlive()) {
            httpClient.connectionPool().put(connection);
        }
        return response;
    }
}
