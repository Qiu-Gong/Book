package com.qiugong.glide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.qiugong.glide.core.Glide;

public class MainActivity extends Activity {

    private ImageView iv1, iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv1 = findViewById(R.id.iv1);
        Glide.with(this)
                .load(Environment.getExternalStorageDirectory() + "/main.jpg")
                .into(iv1);
    }

    public void entry(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}
