package com.qiugong.first.x12_combining.duck.factory;

import com.qiugong.first.x12_combining.duck.ducks.DuckCall;
import com.qiugong.first.x12_combining.duck.ducks.MallardDuck;
import com.qiugong.first.x12_combining.duck.ducks.Quackable;
import com.qiugong.first.x12_combining.duck.ducks.RedheadDuck;
import com.qiugong.first.x12_combining.duck.ducks.RubberDuck;

public class DuckFactory extends AbstractDuckFactory {

    public Quackable createMallardDuck() {
        return new MallardDuck();
    }

    public Quackable createRedheadDuck() {
        return new RedheadDuck();
    }

    public Quackable createDuckCall() {
        return new DuckCall();
    }

    public Quackable createRubberDuck() {
        return new RubberDuck();
    }
}
