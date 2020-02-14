package com.qiugong.rxjava.core;

/**
 * @author qzx 20/2/14.
 */
public abstract class Observable<T> implements ObservableSource<T> {

    public static <T> Observable<T> create(ObservableOnSubscribe<T> source) {
        return new ObservableCreate<>(source);
    }

    public <R> Observable<R> map(Function<? super T, ? extends R> function) {
        return new ObservableMap<>(this, function);
    }

    @Override
    public void subscribe(Observer<? super T> observer) {
        subscribeActual(observer);
    }

    abstract void subscribeActual(Observer<? super T> observer);
}
