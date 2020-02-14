package com.qiugong.rxjava.core;

/**
 * @author qzx 20/2/14.
 */
public interface Observer<T> {

    void onSubscribe(Disposable disposable);

    void onNext(T t);

    void onError(Throwable throwable);

    void onComplete();
}
