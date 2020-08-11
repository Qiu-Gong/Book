package com.qiugong.first.x01_strategy.duck;

import com.qiugong.first.x01_strategy.duck.Duck;
import com.qiugong.first.x01_strategy.fly.FlyWithWings;
import com.qiugong.first.x01_strategy.quack.Quack;

public class MallardDuck extends Duck {

	public MallardDuck() {
		quackBehavior = new Quack();
		flyBehavior = new FlyWithWings();
	}

	public void display() {
		System.out.println("I'm a real Mallard duck");
	}
}
