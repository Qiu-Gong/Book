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

    static final class CreateEmitter<T> implements ObservableEmitter<T>, Disposable {

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
}
