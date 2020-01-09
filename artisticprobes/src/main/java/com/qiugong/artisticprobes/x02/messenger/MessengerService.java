package com.qiugong.artisticprobes.x02.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * @author qzx 2019/4/15.
 */
public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    public static final int MSG_FROM_CLIENT = 0;
    public static final int MSG_FROM_SERVICE = 1;

    private final Messenger messenger = new Messenger(new MessengerHandler());

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FROM_CLIENT:
                    // 收到消息
                    Log.i(TAG, "receive msg from Client:" + msg.getData().getString("msg"));

                    // 回复
                    Messenger client = msg.replyTo;
                    Message replyMessage = Message.obtain(null, MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "嗯，你的消息我已经收到，稍后会回复你。");
                    replyMessage.setData(bundle);
                    try {
                        client.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    super.handleMessage(msg);
            }

            super.handleMessage(msg);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
