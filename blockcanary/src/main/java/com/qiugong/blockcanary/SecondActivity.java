package com.qiugong.blockcanary;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        final TextView text = findViewById(R.id.text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "haha",  LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.btn).setEnabled(false);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float curTranslationX = text.getTranslationX();
                ObjectAnimator animator = ObjectAnimator.ofFloat(text, "translationX", curTranslationX, curTranslationX+500f);
                animator.setDuration(5000);
                animator.start();
            }
        });

    }
}
