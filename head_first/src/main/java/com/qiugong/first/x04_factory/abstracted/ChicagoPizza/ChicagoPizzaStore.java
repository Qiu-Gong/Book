package com.qiugong.first.x04_factory.abstracted.ChicagoPizza;

import com.qiugong.first.x04_factory.abstracted.CheesePizza;
import com.qiugong.first.x04_factory.abstracted.ClamPizza;
import com.qiugong.first.x04_factory.abstracted.PepperoniPizza;
import com.qiugong.first.x04_factory.abstracted.Pizza;
import com.qiugong.first.x04_factory.abstracted.PizzaStore;
import com.qiugong.first.x04_factory.abstracted.VeggiePizza;
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
