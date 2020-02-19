package com.qiugong.glide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qiugong.glide.core.Glide;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Glide.with(this);
    }

    public void entry(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}
