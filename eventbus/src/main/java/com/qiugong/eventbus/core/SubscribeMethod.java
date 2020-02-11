package com.qiugong.eventbus.core;

import java.lang.reflect.Method;

/**
 * @author qzx 20/2/11.
 */
public class SubscribeMethod {

    // 注册方法
    private Method method;
    // 方法参数类型
    private Class<?> type;
    // 线程类型
    private ThreadMode threadMode;

    public SubscribeMethod(Method method, Class<?> type, ThreadMode threadMode) {
        this.method = method;
        this.type = type;
        this.threadMode = threadMode;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getType() {
        return type;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }
}
