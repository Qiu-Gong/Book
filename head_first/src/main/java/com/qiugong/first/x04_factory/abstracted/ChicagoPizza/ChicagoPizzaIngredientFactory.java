package com.qiugong.first.x04_factory.abstracted.ChicagoPizza;

import com.qiugong.first.x04_factory.abstracted.NYPizza.SlicedPepperoni;
import com.qiugong.first.x04_factory.abstracted.ingredient.Cheese;
import com.qiugong.first.x04_factory.abstracted.ingredient.Clams;
import com.qiugong.first.x04_factory.abstracted.ingredient.Dough;
import com.qiugong.first.x04_factory.abstracted.ingredient.Pepperoni;
import com.qiugong.first.x04_factory.abstracted.ingredient.PizzaIngredientFactory;
import com.qiugong.first.x04_factory.abstracted.ingredient.Sauce;
import com.qiugong.first.x04_factory.abstracted.ingredient.Veggies;

public class ChicagoPizzaIngredientFactory
	implements PizzaIngredientFactory
{

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
		Veggies veggies[] = { new BlackOlives(), 
		                      new Spinach(), 
		                      new Eggplant() };
		return veggies;
	}

	public Pepperoni createPepperoni() {
		return new SlicedPepperoni();
	}

	public Clams createClam() {
		return new FrozenClams();
	}
}
