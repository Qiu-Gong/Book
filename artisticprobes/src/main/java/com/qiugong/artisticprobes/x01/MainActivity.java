package com.qiugong.artisticprobes.x01;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.qiugong.artisticprobes.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TextView) findViewById(R.id.text)).setText("MainActivity");

        UserId = 2;
        Log.d(TAG, "UserId:" + UserId);
    }
}
