package com.qiugong.rxjava.core;

/**
 * @author qzx 20/2/14.
 */
public interface Disposable {

    void dispose(boolean disable);

    boolean isDispose();
}
