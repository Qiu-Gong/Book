package com.qiugong.first.x12_combining.decorator;

import com.qiugong.first.x12_combining.ducks.Quackable;
import com.qiugong.first.x12_combining.observer.Observer;

public class QuackCounter implements Quackable {
    Quackable duck;
    static int numberOfQuacks;

    public QuackCounter(Quackable duck) {
        this.duck = duck;
    }

    public void quack() {
        duck.quack();
        numberOfQuacks++;
    }

    public static int getQuacks() {
        return numberOfQuacks;
    }

    @Override
    public void registerObserver(Observer observer) {
        duck.registerObserver(observer);
    }

    @Override
    public void notifyObservers() {
        duck.notifyObservers();
    }

    public String toString() {
        return duck.toString();
    }
}
