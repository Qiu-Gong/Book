package com.qiugong.first.x04_factory.xx03_abstracted.ingredient;

import com.qiugong.first.x04_factory.xx03_abstracted.factory.cheese.Cheese;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.cheese.MozzarellaCheese;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.clams.Clams;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.clams.FrozenClams;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.dough.Dough;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.dough.ThickCrustDough;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.pepperoni.Pepperoni;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.pepperoni.SlicedPepperoni;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.sauce.PlumTomatoSauce;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.sauce.Sauce;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.veggies.BlackOlives;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.veggies.Eggplant;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.veggies.Spinach;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.veggies.Veggies;

public class ChicagoPizzaIngredientFactory implements PizzaIngredientFactory {

    public Dough createDough() {
        return new ThickCrustDough();
    }

    public Sauce createSauce() {
        return new PlumTomatoSauce();
    }

    public Cheese createCheese() {
        return new MozzarellaCheese();
    }

    public Veggies[] createVeggies() {
        Veggies veggies[] = {new BlackOlives(),
                new Spinach(),
                new Eggplant()};
        return veggies;
    }

    public Pepperoni createPepperoni() {
        return new SlicedPepperoni();
    }

    public Clams createClam() {
        return new FrozenClams();
    }
}
