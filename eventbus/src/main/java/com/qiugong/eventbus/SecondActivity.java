package com.qiugong.eventbus;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.qiugong.eventbus.core.EventBus;

/**
 * @author qzx 20/2/11.
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Friend friend = new Friend("Qiu", 18);
                EventBus.getInstance().post(friend);
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getInstance().post("QiuGong");
            }
        });
    }
}
