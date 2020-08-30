package com.qiugong.first.x12_combining.duck.factory;

import com.qiugong.first.x12_combining.duck.ducks.Quackable;

public abstract class AbstractDuckFactory {

    public abstract Quackable createMallardDuck();

    public abstract Quackable createRedheadDuck();

    public abstract Quackable createDuckCall();

    public abstract Quackable createRubberDuck();
}
