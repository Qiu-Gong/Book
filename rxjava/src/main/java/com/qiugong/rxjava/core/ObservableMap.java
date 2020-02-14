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

    static final class MapObserver<T, R> extends BasicFunctionObserver<T, R> {

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
}
