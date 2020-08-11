package com.qiugong.first.x01_strategy.duck;

import com.qiugong.first.x01_strategy.duck.Duck;
import com.qiugong.first.x01_strategy.fly.FlyNoWay;
import com.qiugong.first.x01_strategy.quack.Quack;

public class ModelDuck extends Duck {
	public ModelDuck() {
		flyBehavior = new FlyNoWay();
		quackBehavior = new Quack();
	}

	public void display() {
		System.out.println("I'm a model duck");
	}
}
