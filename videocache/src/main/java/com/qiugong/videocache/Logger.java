package com.qiugong.videocache;

import android.util.Log;

import java.util.Date;

/**
 * @author qzx 20/2/25.
 */
public class Logger {

    private String tag;

    public Logger(String tag) {
        this.tag = tag;
    }

    public void info(String string) {
        Log.i(tag, string);
    }

    public void debug(String string) {
        Log.d(tag, string);
    }

    public void warn(String string, Date date, String absolutePath) {
        Log.d(tag, string + " date:" + date.toString() + " absolutePath:" + absolutePath);
    }

    public void warn(String string) {
        Log.w(tag, string);
    }

    public void warn(String string, Exception error) {
        Log.w(tag, string, error);
    }

    public void error(String string) {
        Log.e(tag, string);
    }

    public void error(String string, Exception e) {
        Log.e(tag, string, e);
    }

    public void error(String string, Throwable e) {
        Log.e(tag, string, e);
    }
}
