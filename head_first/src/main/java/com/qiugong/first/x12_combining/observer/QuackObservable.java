package com.qiugong.first.x12_combining.observer;

public interface QuackObservable {

    public void registerObserver(Observer observer);

    public void notifyObservers();
}
