package com.qiugong.glide;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.qiugong.glide.core.Glide;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Glide.with(this);
    }
}
