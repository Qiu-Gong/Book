package com.qiugong.advance.x15;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qiugong.advance.R;
import com.qiugong.advance.x15.activity.TargetActivity;
import com.qiugong.advance.x15.service.TargetService;
import com.qiugong.advance.x15.tools.HookHelper;

/**
 * @author qzx 20/1/12.
 */
public class PluginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HookHelper.hookAMS();
                    HookHelper.hookHandler();
                    // TargetActivity 没有注册
                    Intent intent = new Intent(PluginActivity.this, TargetActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // TargetActivity 没有注册
                    Intent intent = new Intent(PluginActivity.this, TargetActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HookHelper.hookServiceAMS();
                    Intent intent = new Intent(PluginActivity.this, TargetService.class);
                    startService(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
