package com.qiugong.first.x12_combining.duck.ducks;

import com.qiugong.first.x12_combining.duck.observer.Observable;
import com.qiugong.first.x12_combining.duck.observer.Observer;

public class DecoyDuck implements Quackable {
    Observable observable;

    public DecoyDuck() {
        observable = new Observable(this);
    }

    public void quack() {
        System.out.println("<< Silence >>");
        notifyObservers();
    }

    public void registerObserver(Observer observer) {
        observable.registerObserver(observer);
    }

    public void notifyObservers() {
        observable.notifyObservers();
    }

    public String toString() {
        return "Decoy Duck";
    }
}
