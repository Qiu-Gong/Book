package com.qiugong.glide;

import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.qiugong.glide.core.Glide;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ImageView iv1 = findViewById(R.id.iv1);
        Glide.with(this)
                .load(Environment.getExternalStorageDirectory() + "/main.jpg")
                .into(iv1);
    }
}
