package com.qiugong.first.x04_factory.xx03_abstracted.ingredient;

import com.qiugong.first.x04_factory.xx03_abstracted.factory.cheese.Cheese;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.clams.Clams;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.dough.Dough;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.pepperoni.Pepperoni;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.sauce.Sauce;
import com.qiugong.first.x04_factory.xx03_abstracted.factory.veggies.Veggies;

public interface PizzaIngredientFactory {

    public Dough createDough();

    public Sauce createSauce();

    public Cheese createCheese();

    public Veggies[] createVeggies();

    public Pepperoni createPepperoni();

    public Clams createClam();

}
