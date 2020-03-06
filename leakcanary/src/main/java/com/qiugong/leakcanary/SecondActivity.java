package com.qiugong.leakcanary;

import android.app.Activity;
import android.os.Bundle;

public class SecondActivity extends Activity {

    public static SecondActivity activity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        activity = this;
    }
}
