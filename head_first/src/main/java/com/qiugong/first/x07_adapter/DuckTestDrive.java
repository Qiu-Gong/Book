package com.qiugong.first.x07_adapter;

public class DuckTestDrive {
    public static void main(String[] args) {
        MallardDuck duck = new MallardDuck();

        WildTurkey turkey = new WildTurkey();
        Duck turkeyAdapter = new TurkeyAdapter(turkey);

        // Gobble gobble
        // I'm flying a short distance
        System.out.println("The Turkey says...");
        turkey.gobble();
        turkey.fly();

        // Quack
        // I'm flying
        System.out.println("\nThe Duck says...");
        testDuck(duck);

        // Gobble gobble
        // I'm flying a short distance
        // I'm flying a short distance
        // I'm flying a short distance
        // I'm flying a short distance
        // I'm flying a short distance
        System.out.println("\nThe TurkeyAdapter says...");
        testDuck(turkeyAdapter);
    }

    static void testDuck(Duck duck) {
        duck.quack();
        duck.fly();
    }
}
