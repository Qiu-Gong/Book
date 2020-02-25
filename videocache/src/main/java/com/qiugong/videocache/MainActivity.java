package com.qiugong.videocache;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.danikula.videocache.HttpProxyCacheServer;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoView videoView = findViewById(R.id.videoView);

        App app = (App) getApplication();
        HttpProxyCacheServer proxy = app.getProxy(this);

        String proxyUrl = proxy.getProxyUrl("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4 ");
        Log.d(TAG, "onCreate: proxyUrl=" + proxyUrl);
        videoView.setVideoPath(proxyUrl);
        videoView.start();
    }
}
