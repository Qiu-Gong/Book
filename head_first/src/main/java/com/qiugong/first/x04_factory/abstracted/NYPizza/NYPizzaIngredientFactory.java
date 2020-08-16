package com.qiugong.first.x04_factory.abstracted.NYPizza;

import com.qiugong.first.x04_factory.abstracted.ingredient.Cheese;
import com.qiugong.first.x04_factory.abstracted.ingredient.Clams;
import com.qiugong.first.x04_factory.abstracted.ingredient.Dough;
import com.qiugong.first.x04_factory.abstracted.ingredient.Pepperoni;
import com.qiugong.first.x04_factory.abstracted.ingredient.PizzaIngredientFactory;
import com.qiugong.first.x04_factory.abstracted.ingredient.Sauce;
import com.qiugong.first.x04_factory.abstracted.ingredient.Veggies;

public class NYPizzaIngredientFactory implements PizzaIngredientFactory {

    public Dough createDough() {
        return new ThinCrustDough();
    }

    public Sauce createSauce() {
        return new MarinaraSauce();
    }

    public Cheese createCheese() {
        return new ReggianoCheese();
    }

    public Veggies[] createVeggies() {
        Veggies veggies[] = {new Garlic(), new Onion(), new Mushroom(), new RedPepper()};
        return veggies;
    }

    public Pepperoni createPepperoni() {
        return new SlicedPepperoni();
    }

    public Clams createClam() {
        return new FreshClams();
    }
}
