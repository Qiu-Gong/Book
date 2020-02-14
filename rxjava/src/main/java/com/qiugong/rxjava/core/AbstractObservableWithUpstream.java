package com.qiugong.rxjava.core;

/**
 * @author qzx 20/2/14.
 */
abstract class AbstractObservableWithUpstream<T, R> extends Observable<R> {

    final ObservableSource<T> source;

    AbstractObservableWithUpstream(ObservableSource<T> source) {
        this.source = source;
    }
}
