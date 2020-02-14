package com.qiugong.rxjava.core;

/**
 * @author qzx 20/2/14.
 */
public interface ObservableSource<T> {

    void subscribe(Observer<? super T> observer);
}
