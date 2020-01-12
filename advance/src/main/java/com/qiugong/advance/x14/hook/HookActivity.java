package com.qiugong.advance.x14.hook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qiugong.advance.R;

public class HookActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1.hook Activity
                InstrumentationProxy.replaceActivityInstrumentation(HookActivity.this);
                Intent intent = new Intent(HookActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 2.hook context
                InstrumentationProxy.replaceContextInstrumentation();
                Intent intent = new Intent(HookActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
    }

}
