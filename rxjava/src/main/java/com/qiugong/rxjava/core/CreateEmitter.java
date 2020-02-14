package com.qiugong.rxjava.core;

/**
 * @author qzx 20/2/15.
 */
public class CreateEmitter<T> implements ObservableEmitter<T>, Disposable {

    final Observer<? super T> observer;
    private boolean disable;

    CreateEmitter(Observer<? super T> observer) {
        this.observer = observer;
    }

    @Override
    public void dispose(boolean disable) {
        this.disable = disable;
    }

    @Override
    public boolean isDispose() {
        return disable;
    }

    @Override
    public void onNext(T value) {
        if (!disable) {
            observer.onNext(value);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (!disable) {
            observer.onError(throwable);
        }
    }

    @Override
    public void onComplete() {
        if (!disable) {
            observer.onComplete();
        }
    }
}
