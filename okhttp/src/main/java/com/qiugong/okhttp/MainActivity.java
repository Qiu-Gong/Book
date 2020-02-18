package com.qiugong.okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qiugong.okhttp.net.Call;
import com.qiugong.okhttp.net.Callback;
import com.qiugong.okhttp.net.HttpClient;
import com.qiugong.okhttp.net.Request;
import com.qiugong.okhttp.net.RequestBody;
import com.qiugong.okhttp.net.Response;

import java.io.IOException;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private HttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpClient = new HttpClient();

        findViewById(R.id.ssl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TestSsl.createSSL(MainActivity.this.getApplicationContext());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        findViewById(R.id.socket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TestSocket.createSocket();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        findViewById(R.id.get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Request request = new Request.Builder()
                        .url("https://www.baidu.com/")
                        .get()
                        .build();
                httpClient.call(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, Throwable throwable) {
                        Log.d(TAG, "onFailure: " + throwable.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
//                        Log.d(TAG, "onResponse: " + response.getBody());
                    }
                });
            }
        });

        findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody body = new RequestBody()
                        .add("city", "长沙")
                        .add("key", "13cb58f5884f9749287abbead9c658f2");
                Request request = new Request.Builder()
                        .url("http://restapi.amap.com/v3/weather/weatherInfo")
                        .post(body)
                        .build();
                httpClient.call(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, Throwable throwable) {
                        Log.d(TAG, "onFailure: " + throwable.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        Log.d(TAG, "onResponse: " + response.getBody());
                    }
                });
            }
        });
    }
}
