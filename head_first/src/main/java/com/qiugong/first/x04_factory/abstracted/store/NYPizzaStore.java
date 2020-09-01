package com.qiugong.first.x04_factory.abstracted.store;

import com.qiugong.first.x04_factory.abstracted.pizza.CheesePizza;
import com.qiugong.first.x04_factory.abstracted.pizza.ClamPizza;
import com.qiugong.first.x04_factory.abstracted.pizza.PepperoniPizza;
import com.qiugong.first.x04_factory.abstracted.pizza.Pizza;
import com.qiugong.first.x04_factory.abstracted.pizza.VeggiePizza;
import com.qiugong.first.x04_factory.abstracted.ingredient.NYPizzaIngredientFactory;
import com.qiugong.first.x04_factory.abstracted.ingredient.PizzaIngredientFactory;

public class NYPizzaStore extends PizzaStore {
 
	protected Pizza createPizza(String item) {
		Pizza pizza = null;
		PizzaIngredientFactory ingredientFactory =
			new NYPizzaIngredientFactory();
 
		if (item.equals("cheese")) {
  
			pizza = new CheesePizza(ingredientFactory);
			pizza.setName("New York Style Cheese Pizza");
  
		} else if (item.equals("veggie")) {
 
			pizza = new VeggiePizza(ingredientFactory);
			pizza.setName("New York Style Veggie Pizza");
 
		} else if (item.equals("clam")) {
 
			pizza = new ClamPizza(ingredientFactory);
			pizza.setName("New York Style Clam Pizza");
 
		} else if (item.equals("pepperoni")) {

			pizza = new PepperoniPizza(ingredientFactory);
			pizza.setName("New York Style Pepperoni Pizza");
 
		} 
		return pizza;
	}
}
