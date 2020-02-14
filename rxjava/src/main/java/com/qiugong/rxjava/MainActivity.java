package com.qiugong.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qiugong.rxjava.core.Disposable;
import com.qiugong.rxjava.core.Function;
import com.qiugong.rxjava.core.Observable;
import com.qiugong.rxjava.core.ObservableEmitter;
import com.qiugong.rxjava.core.ObservableOnSubscribe;
import com.qiugong.rxjava.core.Observer;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) {
                        emitter.onNext("Qiu");
                        emitter.onNext("QiuGong");
                        emitter.onComplete();
                    }
                }).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        Log.d(TAG, "onSubscribe:" + disposable.isDispose());
                    }

                    @Override
                    public void onNext(String string) {
                        Log.d(TAG, "onNext: " + string);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG, "onError: " + throwable.getMessage());
                    }
                });
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onComplete();
                    }
                }).map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        Log.d(TAG, "apply: source=" + Integer.class.getSimpleName()
                                + " to=" + String.class.getSimpleName());
                        return String.valueOf(integer);
                    }
                }).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        Log.d(TAG, "onSubscribe:" + disposable.isDispose());
                    }

                    @Override
                    public void onNext(String string) {
                        Log.d(TAG, "onNext: " + string);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG, "onError: " + throwable.getMessage());
                    }
                });
            }
        });
    }
}
