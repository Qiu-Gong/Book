package com.qiugong.okhttp.net;

import android.text.TextUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author qzx 20/2/18.
 */
public class HttpUrl {

    private String protocol;
    private String host;
    private String file;
    private int port;

    HttpUrl(String urlPath) {
        try {
            URL url = new URL(urlPath);
            host = url.getHost();
            protocol = url.getProtocol();
            port = url.getPort();
            port = url.getPort() == -1 ? url.getDefaultPort() : url.getPort();
            file = TextUtils.isEmpty(url.getFile()) ? "/" : url.getFile();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    String getProtocol() {
        return protocol;
    }

    String getFile() {
        return file;
    }
}
