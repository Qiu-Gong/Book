package com.qiugong.advance.x15.tools;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author qzx 2019/8/27.
 */
public class HCallback implements Handler.Callback {

    private static final String TAG = "HCallback";
    public static final int LAUNCH_ACTIVITY = 100;
    private Handler mHandler;

    public HCallback(Handler handler) {
        this.mHandler = handler;
    }

    @Override
    public boolean handleMessage(Message msg) {
        Log.d(TAG, "hook handleMessage:" + msg.what);
        if (msg.what == LAUNCH_ACTIVITY) {
            Object r = msg.obj;
            try {
                Intent intent = (Intent) FieldUtil.getField(r.getClass(), r, "intent");
                Intent target = intent.getParcelableExtra(HookHelper.TARGET_INTENT);
                intent.setComponent(target.getComponent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.mHandler.handleMessage(msg);
        return true;
    }
}
