package com.qiugong.encryptapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "activity:" + getApplication());
        Log.i(TAG, "activity:" + getApplicationContext());
        Log.i(TAG, "activity:" + getApplicationInfo().className);

        findViewById(R.id.service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, MyService.class));
            }
        });

        findViewById(R.id.broadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.qiugong.encryptapp.test");
                intent.setPackage(getPackageName());
                MainActivity.this.sendBroadcast(intent);
            }
        });

        findViewById(R.id.provider).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.qiugong.encryptapp.MyProvider");
                MainActivity.this.getContentResolver().delete(uri, null, null);
            }
        });
    }
}
