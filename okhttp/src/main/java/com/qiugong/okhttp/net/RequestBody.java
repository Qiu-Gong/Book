package com.qiugong.okhttp.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qzx 20/2/18.
 */
public class RequestBody {

    public final static String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private final static String UTF8 = "utf-8";

    private Map<String, String> encodeBody = new HashMap<>();

    public RequestBody add(String key, String value) {
        try {
            encodeBody.put(URLEncoder.encode(key, UTF8), URLEncoder.encode(value, UTF8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String body() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : encodeBody.entrySet()) {
            builder.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        if (builder.length() != 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }
}
