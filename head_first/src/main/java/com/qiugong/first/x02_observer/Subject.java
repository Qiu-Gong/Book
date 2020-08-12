package com.qiugong.first.x02_observer;

import com.qiugong.first.x02_observer.observer.Observer;

public interface Subject {
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers();
}
