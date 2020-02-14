package com.qiugong.rxjava.core;

/**
 * @author qzx 20/2/14.
 */
class ObservableMap<T, R> extends AbstractObservableWithUpstream<T, R> {

    private final Function<? super T, ? extends R> function;

    ObservableMap(ObservableSource<T> source, Function<? super T, ? extends R> function) {
        super(source);
        this.function = function;
    }

    @Override
    void subscribeActual(Observer<? super R> observer) {
        source.subscribe(new MapObserver<>(observer, function));
    }
}
