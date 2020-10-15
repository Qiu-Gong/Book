package com.qiugong.first.x04_factory.xx03_abstracted.store;

import com.qiugong.first.x04_factory.xx03_abstracted.pizza.CheesePizza;
import com.qiugong.first.x04_factory.xx03_abstracted.pizza.ClamPizza;
import com.qiugong.first.x04_factory.xx03_abstracted.pizza.PepperoniPizza;
import com.qiugong.first.x04_factory.xx03_abstracted.pizza.Pizza;
import com.qiugong.first.x04_factory.xx03_abstracted.pizza.VeggiePizza;
import com.qiugong.first.x04_factory.xx03_abstracted.ingredient.NYPizzaIngredientFactory;
import com.qiugong.first.x04_factory.xx03_abstracted.ingredient.PizzaIngredientFactory;

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
