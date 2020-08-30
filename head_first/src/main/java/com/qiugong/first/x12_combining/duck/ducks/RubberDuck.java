package com.qiugong.first.x12_combining.duck.ducks;

import com.qiugong.first.x12_combining.duck.observer.Observable;
import com.qiugong.first.x12_combining.duck.observer.Observer;

public class RubberDuck implements Quackable {
	Observable observable;

	public RubberDuck() {
		observable = new Observable(this);
	}

	public void quack() {
		System.out.println("Squeak");
		notifyObservers();
	}

	public void registerObserver(Observer observer) {
		observable.registerObserver(observer);
	}

	public void notifyObservers() {
		observable.notifyObservers();
	}

	public String toString() {
		return "Rubber Duck";
	}
}
