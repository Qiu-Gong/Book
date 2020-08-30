package com.qiugong.first.x12_combining.adapter;

import com.qiugong.first.x12_combining.ducks.Quackable;
import com.qiugong.first.x12_combining.observer.Observable;
import com.qiugong.first.x12_combining.observer.Observer;

public class GooseAdapter implements Quackable {
    Goose goose;
    Observable observable;

    public GooseAdapter(Goose goose) {
        this.goose = goose;
        observable = new Observable(this);
    }

    public void quack() {
        goose.honk();
        notifyObservers();
    }

    public void registerObserver(Observer observer) {
        observable.registerObserver(observer);
    }

    public void notifyObservers() {
        observable.notifyObservers();
    }

    public String toString() {
        return "Goose pretending to be a Duck";
    }
}
