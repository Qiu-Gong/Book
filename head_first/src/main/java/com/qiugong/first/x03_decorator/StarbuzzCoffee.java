package com.qiugong.first.x03_decorator;

import com.qiugong.first.x03_decorator.beverage.Beverage;
import com.qiugong.first.x03_decorator.beverage.DarkRoast;
import com.qiugong.first.x03_decorator.beverage.Espresso;
import com.qiugong.first.x03_decorator.beverage.HouseBlend;
import com.qiugong.first.x03_decorator.decorator.Mocha;
import com.qiugong.first.x03_decorator.decorator.Soy;
import com.qiugong.first.x03_decorator.decorator.Whip;

public class StarbuzzCoffee {

    public static void main(String args[]) {
        Beverage beverage = new Espresso();
        // Espresso $1.99
        System.out.println(beverage.getDescription()
                + " $" + beverage.cost());

        // Dark Roast Coffee, Mocha, Mocha, Whip $1.49
        Beverage beverage2 = new DarkRoast();
        beverage2 = new Mocha(beverage2);
        beverage2 = new Mocha(beverage2);
        beverage2 = new Whip(beverage2);
        System.out.println(beverage2.getDescription()
                + " $" + beverage2.cost());

        // House Blend Coffee, Soy, Mocha, Whip $1.34
        Beverage beverage3 = new HouseBlend();
        beverage3 = new Soy(beverage3);
        beverage3 = new Mocha(beverage3);
        beverage3 = new Whip(beverage3);
        System.out.println(beverage3.getDescription()
                + " $" + beverage3.cost());
    }
}
