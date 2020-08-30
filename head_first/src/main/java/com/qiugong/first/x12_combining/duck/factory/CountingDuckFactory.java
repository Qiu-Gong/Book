package com.qiugong.first.x12_combining.duck.factory;

import com.qiugong.first.x12_combining.duck.decorator.QuackCounter;
import com.qiugong.first.x12_combining.duck.ducks.DuckCall;
import com.qiugong.first.x12_combining.duck.ducks.MallardDuck;
import com.qiugong.first.x12_combining.duck.ducks.Quackable;
import com.qiugong.first.x12_combining.duck.ducks.RedheadDuck;
import com.qiugong.first.x12_combining.duck.ducks.RubberDuck;

public class CountingDuckFactory extends AbstractDuckFactory {
  
	public Quackable createMallardDuck() {
		return new QuackCounter(new MallardDuck());
	}
  
	public Quackable createRedheadDuck() {
		return new QuackCounter(new RedheadDuck());
	}
  
	public Quackable createDuckCall() {
		return new QuackCounter(new DuckCall());
	}
   
	public Quackable createRubberDuck() {
		return new QuackCounter(new RubberDuck());
	}
}
