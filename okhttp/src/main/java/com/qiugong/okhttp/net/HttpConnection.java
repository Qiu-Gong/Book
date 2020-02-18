package com.qiugong.okhttp.net;

import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

/**
 * @author qzx 20/2/18.
 */
public class HttpConnection {

    private static final String HTTPS = "https";

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private long lastUpdateTime;
    private Request request;

    public void setRequest(Request request) {
        this.request = request;
    }

    boolean isSameAddress(String host, int port) {
        if (null == socket) return false;
        return TextUtils.equals(socket.getInetAddress().getHostName(), host) &&
                port == socket.getPort();
    }

    private void createSocket() throws IOException {
        if (null == socket || socket.isClosed()) {
            HttpUrl url = request.url();
            if (url.getProtocol().equalsIgnoreCase(HTTPS)) {
                socket = SSLSocketFactory.getDefault().createSocket();
            } else {
                socket = new Socket();
            }
            socket.connect(new InetSocketAddress(url.getHost(), url.getPort()));
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
        }
    }

    public InputStream call(HttpCodec httpCodec) throws IOException {
        try {
            createSocket();
            httpCodec.writeRequest(outputStream, request);
            return inputStream;
        } catch (IOException e) {
            closeSocket();
            throw new IOException(e);
        }
    }

    void closeSocket() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void refreshLastUpdateTime() {
        lastUpdateTime = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "HttpConnection{" +
                "socket address=" + socket.getInetAddress() +
                " port=" + socket.getPort() +
                '}';
    }
}
