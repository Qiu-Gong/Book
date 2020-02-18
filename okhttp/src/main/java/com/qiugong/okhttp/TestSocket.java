package com.qiugong.okhttp;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author qzx 20/2/17.
 */
public class TestSocket {

    private static final String TAG = "TestSocket";

    public static void createSocket() throws IOException {
        Socket socket = new Socket("www.weather.com.cn", 80);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(("GET /weather1d/101230101.shtml HTTP/1.1\r\n" +
                "Host: www.weather.com.cn\r\n\r\n").getBytes());
        outputStream.flush();

        byte[] bytes = new byte[1024];
        int len;
        while ((len = socket.getInputStream().read(bytes)) != -1) {
            String string = new String(bytes, 0, len);
            Log.d(TAG, "read: " + string);
        }
    }
}
