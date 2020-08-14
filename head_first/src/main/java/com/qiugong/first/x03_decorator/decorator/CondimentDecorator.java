package com.qiugong.first.x03_decorator.decorator;

import com.qiugong.first.x03_decorator.beverage.Beverage;

public abstract class CondimentDecorator extends Beverage {
	public Beverage beverage;
	public abstract String getDescription();
	
	public Size getSize() {
		return beverage.getSize();
	}
}
