package com.qiugong.okhttp.net.chain;

import android.util.Log;

import com.qiugong.okhttp.net.HttpCodec;
import com.qiugong.okhttp.net.HttpConnection;
import com.qiugong.okhttp.net.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author qzx 20/2/18.
 */
public class CallServiceInterceptor implements Interceptor {

    private static final String TAG = "CallServiceInterceptor";

    @Override
    public Response intercept(InterceptorChain chain) throws IOException {
        Log.d(TAG, "CallServiceInterceptor intercept...");

        HttpCodec httpCodec = new HttpCodec();
        HttpConnection connection = chain.getConnection();
        InputStream inputStream = connection.call(httpCodec);
        String statusLine = httpCodec.readLine(inputStream);

        // 是否保持连接
        boolean isKeepAlive = false;
        Map<String, String> headers = httpCodec.readHeaders(inputStream);
        if (headers.containsKey(HttpCodec.HEAD_CONNECTION)) {
            isKeepAlive = headers.get(HttpCodec.HEAD_CONNECTION)
                    .equalsIgnoreCase(HttpCodec.HEAD_VALUE_KEEP_ALIVE);
        }

        // content长度
        int contentLength = -1;
        if (headers.containsKey(HttpCodec.HEAD_CONTENT_LENGTH)) {
            contentLength = Integer.valueOf(headers.get(HttpCodec.HEAD_CONTENT_LENGTH));
        }

        // 分块编码数据
        boolean isChunk = false;
        if (headers.containsKey(HttpCodec.HEAD_TRANSFER_ENCODING)) {
            isChunk = headers.get(HttpCodec.HEAD_TRANSFER_ENCODING)
                    .equalsIgnoreCase(HttpCodec.HEAD_VALUE_CHUNK);
        }

        String body = null;
        if (contentLength > 0) {
            byte[] bytes = httpCodec.readBytes(inputStream, contentLength);
            body = new String(bytes);
        } else if (isChunk) {
            body = httpCodec.readChunk(inputStream);
        }

        String[] status = statusLine.split(" ");
        connection.refreshLastUpdateTime();
        return new Response(Integer.valueOf(status[1]), contentLength, headers, body, isKeepAlive);
    }
}
