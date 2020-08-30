package com.qiugong.first.x12_combining.ducks;

import com.qiugong.first.x12_combining.observer.Observable;
import com.qiugong.first.x12_combining.observer.Observer;

public class MallardDuck implements Quackable {
    Observable observable;

    public MallardDuck() {
        observable = new Observable(this);
    }

    @Override
    public void quack() {
        System.out.println("Quack");
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        observable.registerObserver(observer);
    }

    @Override
    public void notifyObservers() {
        observable.notifyObservers();
    }

    public String toString() {
        return "Mallard Duck";
    }
}
