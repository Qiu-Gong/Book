package com.qiugong.first.x01_strategy.duck;

import com.qiugong.first.x01_strategy.fly.FlyBehavior;
import com.qiugong.first.x01_strategy.fly.FlyNoWay;
import com.qiugong.first.x01_strategy.quack.QuackBehavior;

public class RubberDuck extends Duck {
 
	public RubberDuck() {
		flyBehavior = new FlyNoWay();
		//quackBehavior = new Squeak();
		quackBehavior = () -> System.out.println("Squeak");
	}
	
	public RubberDuck(FlyBehavior flyBehavior, QuackBehavior quackBehavior) {
		this.flyBehavior = flyBehavior;
		this.quackBehavior = quackBehavior; 
	}
 
	public void display() {
		System.out.println("I'm a rubber duckie");
	}
}
