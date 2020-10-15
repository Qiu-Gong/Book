package com.qiugong.first.x04_factory.xx02_factory.chicago;

import com.qiugong.first.x04_factory.xx02_factory.Pizza;
import com.qiugong.first.x04_factory.xx02_factory.PizzaStore;

public class ChicagoPizzaStore extends PizzaStore {

    public Pizza createPizza(String item) {
        if (item.equals("cheese")) {
            return new ChicagoStyleCheesePizza();
        } else if (item.equals("veggie")) {
            return new ChicagoStyleVeggiePizza();
        } else if (item.equals("clam")) {
            return new ChicagoStyleClamPizza();
        } else if (item.equals("pepperoni")) {
            return new ChicagoStylePepperoniPizza();
        } else return null;
    }
}
