package com.qiugong.first.x12_combining.factory;

import com.qiugong.first.x12_combining.ducks.DuckCall;
import com.qiugong.first.x12_combining.ducks.MallardDuck;
import com.qiugong.first.x12_combining.ducks.Quackable;
import com.qiugong.first.x12_combining.ducks.RedheadDuck;
import com.qiugong.first.x12_combining.ducks.RubberDuck;

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
