package com.qiugong.dongnao.x02;

import android.app.Activity;
import android.os.Bundle;

import com.qiugong.dongnao.R;

/**
 * @author qzx 20/2/10.
 */
public class HandlerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                QLooper.prepare();

                final QHandler handler = new QHandler() {
                    @Override
                    public void handleMessage(QMessage msg) {
                        System.out.println(msg.obj.toString());
                    }
                };

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        QMessage message = new QMessage();
                        message.what = 1;
                        message.obj = "大家好！";
                        handler.sendMessage(message);
                    }
                }).start();

                QLooper.loop();
            }
        }).start();
    }
}
