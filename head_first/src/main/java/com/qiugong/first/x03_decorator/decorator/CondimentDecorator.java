package com.qiugong.first.x03_decorator.decorator;

import com.qiugong.first.x03_decorator.beverage.Beverage;

public abstract class CondimentDecorator extends Beverage {
	protected Beverage beverage;
	public abstract String getDescription();
}
