package com.qiugong.first.x04_factory.xx03_abstracted.ingredient;

import com.qiugong.first.x04_factory.xx03_abstracted.factory.cheese.ReggianoCheese;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.clams.FreshClams;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.dough.ThinCrustDough;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.cheese.Cheese;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.clams.Clams;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.dough.Dough;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.pepperoni.SlicedPepperoni;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.veggies.Garlic;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.veggies.Mushroom;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.veggies.Onion;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.veggies.RedPepper;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.pepperoni.Pepperoni;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.sauce.MarinaraSauce;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.sauce.Sauce;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.veggies.Veggies;

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
