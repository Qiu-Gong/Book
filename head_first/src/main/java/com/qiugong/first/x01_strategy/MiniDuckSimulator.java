package com.qiugong.first.x01_strategy;

import com.qiugong.first.x01_strategy.duck.DecoyDuck;
import com.qiugong.first.x01_strategy.duck.Duck;
import com.qiugong.first.x01_strategy.duck.MallardDuck;
import com.qiugong.first.x01_strategy.duck.ModelDuck;
import com.qiugong.first.x01_strategy.duck.RubberDuck;
import com.qiugong.first.x01_strategy.fly.FlyBehavior;
import com.qiugong.first.x01_strategy.fly.FlyRocketPowered;
import com.qiugong.first.x01_strategy.quack.QuackBehavior;

public class MiniDuckSimulator {
 
	public static void main(String[] args) {
 
		MallardDuck mallard = new MallardDuck();
		FlyBehavior cantFly = () -> System.out.println("I can't fly");
		QuackBehavior squeak = () -> System.out.println("Squeak");
		RubberDuck rubberDuckie = new RubberDuck(cantFly, squeak);
		mallard.performQuack();// Quack
		rubberDuckie.performQuack();// Squeak

		DecoyDuck decoy = new DecoyDuck();
		decoy.performQuack();// << Silence >>

		Duck model = new ModelDuck();
		model.performFly();	// I can't fly
		model.setFlyBehavior(new FlyRocketPowered());
		model.performFly(); // I'm flying with a rocket
	}
}
