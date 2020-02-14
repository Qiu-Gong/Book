package com.qiugong.rxjava.core;

/**
 * @author qzx 20/2/15.
 */
public class MapObserver<T, R> extends BasicFunctionObserver<T, R> {

    final Function<? super T, ? extends R> function;

    MapObserver(Observer<? super R> actual, Function<? super T, ? extends R> function) {
        super(actual);
        this.function = function;
    }

    @Override
    public void onNext(T t) {
        R apply = function.apply(t);
        actual.onNext(apply);
    }
}