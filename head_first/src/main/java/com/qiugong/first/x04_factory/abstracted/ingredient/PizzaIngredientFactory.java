package com.qiugong.first.x04_factory.abstracted.ingredient;

import com.qiugong.first.x04_factory.abstracted.factory.cheese.Cheese;
import com.qiugong.first.x04_factory.abstracted.factory.clams.Clams;
import com.qiugong.first.x04_factory.abstracted.factory.dough.Dough;
import com.qiugong.first.x04_factory.abstracted.factory.pepperoni.Pepperoni;
import com.qiugong.first.x04_factory.abstracted.factory.sauce.Sauce;
import com.qiugong.first.x04_factory.abstracted.factory.veggies.Veggies;

public interface PizzaIngredientFactory {

    public Dough createDough();

    public Sauce createSauce();

    public Cheese createCheese();

    public Veggies[] createVeggies();

    public Pepperoni createPepperoni();

    public Clams createClam();

}
