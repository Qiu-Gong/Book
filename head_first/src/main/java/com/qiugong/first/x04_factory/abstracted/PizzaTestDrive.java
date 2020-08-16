package com.qiugong.first.x04_factory.abstracted;

import com.qiugong.first.x04_factory.abstracted.ChicagoPizza.ChicagoPizzaStore;
import com.qiugong.first.x04_factory.abstracted.NYPizza.NYPizzaStore;

public class PizzaTestDrive {

    public static void main(String[] args) {
    	// 1. PizzaStore nyStore = new NYPizzaStore();
		// 2. nyStore.orderPizza("cheese");
		// 3. Pizza pizza = createPizza("cheese");
		// 4. Pizza pizza = new CheesePizza(ingredientFactory);
		// 5. void prepare()
		// 6. bake() -> cut() -> box()


        PizzaStore nyStore = new NYPizzaStore();
        PizzaStore chicagoStore = new ChicagoPizzaStore();

        Pizza pizza = nyStore.orderPizza("cheese");
        System.out.println("Ethan ordered a " + pizza + "\n");

        pizza = chicagoStore.orderPizza("cheese");
        System.out.println("Joel ordered a " + pizza + "\n");

        pizza = nyStore.orderPizza("clam");
        System.out.println("Ethan ordered a " + pizza + "\n");

        pizza = chicagoStore.orderPizza("clam");
        System.out.println("Joel ordered a " + pizza + "\n");

        pizza = nyStore.orderPizza("pepperoni");
        System.out.println("Ethan ordered a " + pizza + "\n");

        pizza = chicagoStore.orderPizza("pepperoni");
        System.out.println("Joel ordered a " + pizza + "\n");

        pizza = nyStore.orderPizza("veggie");
        System.out.println("Ethan ordered a " + pizza + "\n");

        pizza = chicagoStore.orderPizza("veggie");
        System.out.println("Joel ordered a " + pizza + "\n");
    }
}
