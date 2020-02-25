package com.qiugong.videocache;

/**
 * @author qzx 20/2/25.
 */
public class LoggerFactory extends Logger {

    public LoggerFactory(String tag) {
        super(tag);
    }

    public static Logger getLogger(String tag) {
        return new LoggerFactory(tag);
    }
}
