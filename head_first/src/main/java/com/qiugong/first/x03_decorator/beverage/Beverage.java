package com.qiugong.first.x03_decorator.beverage;

public abstract class Beverage {

	String description = "Unknown Beverage";
  
	public String getDescription() {
		return description;
	}
 
	public abstract double cost();
}
