package com.qiugong.first.x04_factory.xx01_simple;

import com.qiugong.first.x04_factory.xx01_simple.pizza.CheesePizza;
import com.qiugong.first.x04_factory.xx01_simple.pizza.ClamPizza;
import com.qiugong.first.x04_factory.xx01_simple.pizza.PepperoniPizza;
import com.qiugong.first.x04_factory.xx01_simple.pizza.Pizza;
import com.qiugong.first.x04_factory.xx01_simple.pizza.VeggiePizza;

public class SimplePizzaFactory {

	public Pizza createPizza(String type) {
		Pizza pizza = null;

		if (type.equals("cheese")) {
			pizza = new CheesePizza();
		} else if (type.equals("pepperoni")) {
			pizza = new PepperoniPizza();
		} else if (type.equals("clam")) {
			pizza = new ClamPizza();
		} else if (type.equals("veggie")) {
			pizza = new VeggiePizza();
		}
		return pizza;
	}
}
