package com.qiugong.artisticprobes;

import android.util.Log;

/**
 * @author qzx 20/1/9.
 */
public class Tools {

    public static void log(String tag, String message) {
        Log.d(tag, "thread:" + Thread.currentThread().getName() + "->" + message);
    }
}
