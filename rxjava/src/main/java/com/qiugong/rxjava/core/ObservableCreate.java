package com.qiugong.rxjava.core;

/**
 * @author qzx 20/2/14.
 */
class ObservableCreate<T> extends Observable<T> {

    private final ObservableOnSubscribe<T> source;

    ObservableCreate(ObservableOnSubscribe<T> source) {
        this.source = source;
    }

    @Override
    void subscribeActual(Observer<? super T> observer) {
        CreateEmitter<T> parent = new CreateEmitter<>(observer);
        observer.onSubscribe(parent);

        try {
            source.subscribe(parent);
        } catch (Exception e) {
            observer.onError(e);
        }
    }
}
