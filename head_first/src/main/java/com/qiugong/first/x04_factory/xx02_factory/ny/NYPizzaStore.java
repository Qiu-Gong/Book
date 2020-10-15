package com.qiugong.first.x04_factory.xx02_factory.ny;

import com.qiugong.first.x04_factory.xx02_factory.Pizza;
import com.qiugong.first.x04_factory.xx02_factory.PizzaStore;

public class NYPizzaStore extends PizzaStore {

    public Pizza createPizza(String item) {
        if (item.equals("cheese")) {
            return new NYStyleCheesePizza();
        } else if (item.equals("veggie")) {
            return new NYStyleVeggiePizza();
        } else if (item.equals("clam")) {
            return new NYStyleClamPizza();
        } else if (item.equals("pepperoni")) {
            return new NYStylePepperoniPizza();
        } else return null;
    }
}
