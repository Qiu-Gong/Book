package com.qiugong.first.x01_strategy;

import com.qiugong.first.x01_strategy.duck.Duck;
import com.qiugong.first.x01_strategy.duck.MallardDuck;
import com.qiugong.first.x01_strategy.duck.ModelDuck;
import com.qiugong.first.x01_strategy.fly.FlyRocketPowered;

public class MiniDuckSimulator1 {

    public static void main(String[] args) {
        MallardDuck mallard = new MallardDuck();
        mallard.performQuack(); // Quack
        mallard.performFly(); // I'm flying!!

        Duck model = new ModelDuck();
        model.performFly(); // I can't fly
        model.setFlyBehavior(new FlyRocketPowered());
        model.performFly(); // I'm flying with a rocket
    }
}
