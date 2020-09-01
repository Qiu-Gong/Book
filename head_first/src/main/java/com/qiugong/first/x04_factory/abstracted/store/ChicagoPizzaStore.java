package com.qiugong.first.x04_factory.abstracted.store;

import com.qiugong.first.x04_factory.abstracted.pizza.CheesePizza;
import com.qiugong.first.x04_factory.abstracted.pizza.ClamPizza;
import com.qiugong.first.x04_factory.abstracted.pizza.PepperoniPizza;
import com.qiugong.first.x04_factory.abstracted.pizza.Pizza;
import com.qiugong.first.x04_factory.abstracted.pizza.VeggiePizza;
import com.qiugong.first.x04_factory.abstracted.ingredient.ChicagoPizzaIngredientFactory;
import com.qiugong.first.x04_factory.abstracted.ingredient.PizzaIngredientFactory;

public class ChicagoPizzaStore extends PizzaStore {

    protected Pizza createPizza(String item) {
        Pizza pizza = null;
        PizzaIngredientFactory ingredientFactory =
                new ChicagoPizzaIngredientFactory();

        if (item.equals("cheese")) {

            pizza = new CheesePizza(ingredientFactory);
            pizza.setName("Chicago Style Cheese Pizza");

        } else if (item.equals("veggie")) {

            pizza = new VeggiePizza(ingredientFactory);
            pizza.setName("Chicago Style Veggie Pizza");

        } else if (item.equals("clam")) {

            pizza = new ClamPizza(ingredientFactory);
            pizza.setName("Chicago Style Clam Pizza");

        } else if (item.equals("pepperoni")) {

            pizza = new PepperoniPizza(ingredientFactory);
            pizza.setName("Chicago Style Pepperoni Pizza");

        }
        return pizza;
    }
}
