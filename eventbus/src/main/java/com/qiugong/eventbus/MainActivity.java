package com.qiugong.eventbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qiugong.eventbus.core.EventBus;
import com.qiugong.eventbus.core.Subscribe;
import com.qiugong.eventbus.core.ThreadMode;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getInstance().register(this);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(Friend friend) {
        Log.d(TAG, "thread name:" + Thread.currentThread().getName()
                + " " + friend.toString());
    }

    @Subscribe()
    public void receive2(String string) {
        Log.d(TAG, "thread name:" + Thread.currentThread().getName()
                + " " + string);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }
}
