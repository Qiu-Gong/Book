package com.qiugong.first.x12_combining;

import com.qiugong.first.x12_combining.adapter.Goose;
import com.qiugong.first.x12_combining.adapter.GooseAdapter;
import com.qiugong.first.x12_combining.ducks.DuckCall;
import com.qiugong.first.x12_combining.ducks.MallardDuck;
import com.qiugong.first.x12_combining.ducks.Quackable;
import com.qiugong.first.x12_combining.ducks.RedheadDuck;
import com.qiugong.first.x12_combining.ducks.RubberDuck;

public class DuckSimulator {
	public static void main(String[] args) {
		DuckSimulator simulator = new DuckSimulator();
		simulator.simulate();
	}
  
	void simulate() {
		Quackable mallardDuck = new MallardDuck();
		Quackable redheadDuck = new RedheadDuck();
		Quackable duckCall = new DuckCall();
		Quackable rubberDuck = new RubberDuck();
		Quackable gooseDuck = new GooseAdapter(new Goose());
 
		System.out.println("\nDuck Simulator");
 
		simulate(mallardDuck);
		simulate(redheadDuck);
		simulate(duckCall);
		simulate(rubberDuck);
		simulate(gooseDuck);
	}
   
	void simulate(Quackable duck) {
		duck.quack();
	}
}
