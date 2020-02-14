package com.qiugong.rxjava.core;

/**
 * @author qzx 20/2/14.
 */
public interface ObservableOnSubscribe<T> {

    void subscribe(ObservableEmitter<T> emitter) throws Exception;
}
