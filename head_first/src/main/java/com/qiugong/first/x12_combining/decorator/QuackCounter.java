package com.qiugong.first.x12_combining.decorator;

import com.qiugong.first.x12_combining.ducks.Quackable;

public class QuackCounter implements Quackable {
    Quackable duck;
    static int numberOfQuacks;

    public QuackCounter(Quackable duck) {
        this.duck = duck;
    }

    public void quack() {
        duck.quack();
        numberOfQuacks++;
    }

    public static int getQuacks() {
        return numberOfQuacks;
    }

    public String toString() {
        return duck.toString();
    }
}
