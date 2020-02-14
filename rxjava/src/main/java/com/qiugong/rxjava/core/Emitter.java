package com.qiugong.rxjava.core;

/**
 * @author qzx 20/2/14.
 */
public interface Emitter<T> {

    void onNext(T value);

    void onError(Throwable throwable);

    void onComplete();
}
