package com.qiugong.first.x12_combining.duck.ducks;

import com.qiugong.first.x12_combining.duck.observer.QuackObservable;

public interface Quackable extends QuackObservable {
    public void quack();
}
