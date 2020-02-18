package com.qiugong.okhttp;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * @author qzx 20/2/17.
 */
public class TestSsl {

    private static final String TAG = "TestSsl";

    public static void createSSL(Context context) throws Exception {
        Log.d(TAG, "createSSL: ");
        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManagerFactory trustManager = TrustManagerFactory.getInstance("X509");
        KeyStore keyStore = KeyStore.getInstance("BKS");

        keyStore.load(context.getAssets().open("12306.bks"), "dongnao".toCharArray());
        trustManager.init(keyStore);
        sslContext.init(null, trustManager.getTrustManagers(), null);

        SSLSocketFactory socketFactory = sslContext.getSocketFactory();
        Socket socket = socketFactory.createSocket("www.12306.cn", 443);
        doHttps(socket);
    }

    private static void doHttps(Socket socket) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write("GET / HTTP/1.1\r\n");
        writer.write("Host: www.12306.cn\r\n\r\n");
        writer.flush();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    while (true) {
                        try {
                            String line;
                            if ((line = reader.readLine()) != null) {
                                Log.d("TEST", line);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }).start();
    }
}
