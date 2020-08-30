package com.qiugong.first.x12_combining.ducks;

import com.qiugong.first.x12_combining.observer.QuackObservable;

public interface Quackable extends QuackObservable {
    public void quack();
}
