package com.qiugong.eventbus.core;

/**
 * @author qzx 20/2/11.
 */
public enum ThreadMode {

    /**
     * 同样线程
     */
    POSTING,

    /**
     * 切换线程
     */
    ASYNC,

    /**
     * 主线程
     */
    MAIN,
}
