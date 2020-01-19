package com.qiugong.dongnao.x02;

import org.junit.Test;

/**
 * @author qzx 20/1/19.
 */
public class QHandlerTest {

    @Test
    public static void main(String[] args) {
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
}
