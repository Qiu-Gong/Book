package com.qiugong.glide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.qiugong.glide.core.Glide;
import com.qiugong.glide.core.request.RequestOptions;

import java.io.File;

public class MainActivity extends Activity {

    private ImageView iv1, iv2, iv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);

        Glide.with(this)
                .load(Environment.getExternalStorageDirectory() + "/main.jpg")
                .into(iv1);

        Glide.with(this)
                .load(new File(Environment.getExternalStorageDirectory() + "/zyx.jpg"))
                .into(iv2);

        Glide.with(this)
                .load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3387716082,1768697998&fm=26&gp=0.jpg")
                .apply(new RequestOptions()
                        .error(R.drawable.ic_launcher_background)
                        .plcaeHolder(R.mipmap.ic_launcher)
                        .override(500, 500))
                .into(iv3);
    }

    public void entry(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}
