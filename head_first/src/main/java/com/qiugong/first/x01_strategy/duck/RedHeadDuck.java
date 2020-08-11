package com.qiugong.first.x01_strategy.duck;

import com.qiugong.first.x01_strategy.fly.FlyWithWings;
import com.qiugong.first.x01_strategy.quack.Quack;

public class RedHeadDuck extends Duck {
 
	public RedHeadDuck() {
		flyBehavior = new FlyWithWings();
		quackBehavior = new Quack();
	}
 
	public void display() {
		System.out.println("I'm a real Red Headed duck");
	}
}
