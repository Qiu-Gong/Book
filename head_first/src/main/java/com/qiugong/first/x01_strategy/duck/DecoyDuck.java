package com.qiugong.first.x01_strategy.duck;

import com.qiugong.first.x01_strategy.fly.FlyNoWay;
import com.qiugong.first.x01_strategy.quack.MuteQuack;

public class DecoyDuck extends Duck {
	public DecoyDuck() {
		setFlyBehavior(new FlyNoWay());
		setQuackBehavior(new MuteQuack());
	}
	public void display() {
		System.out.println("I'm a duck Decoy");
	}
}
