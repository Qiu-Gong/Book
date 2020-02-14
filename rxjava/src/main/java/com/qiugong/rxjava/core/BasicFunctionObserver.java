package com.qiugong.rxjava.core;

/**
 * @author qzx 20/2/14.
 */
abstract class BasicFunctionObserver<T, R> implements Observer<T>, Disposable {

    final Observer<? super R> actual;
    private Disposable disposable;

    BasicFunctionObserver(Observer<? super R> actual) {
        this.actual = actual;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
        actual.onSubscribe(this.disposable);
    }

    @Override
    public void onError(Throwable throwable) {
        actual.onError(throwable);
    }

    @Override
    public void onComplete() {
        actual.onComplete();
    }

    @Override
    public void dispose(boolean disable) {
        disposable.dispose(disable);
    }

    @Override
    public boolean isDispose() {
        return disposable.isDispose();
    }
}
