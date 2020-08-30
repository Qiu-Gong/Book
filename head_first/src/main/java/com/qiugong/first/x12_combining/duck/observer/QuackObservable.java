package com.qiugong.first.x12_combining.duck.observer;

public interface QuackObservable {

    public void registerObserver(Observer observer);

    public void notifyObservers();
}
