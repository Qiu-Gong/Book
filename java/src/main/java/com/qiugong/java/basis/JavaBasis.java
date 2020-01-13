package com.qiugong.java.basis;

/**
 * @author qzx 20/1/13.
 */
public class JavaBasis {

    public static void main(String[] args) {
        Integer x = new Integer(123);
        Integer y = new Integer(123);
        System.out.println((x == y) + "->false");

        Integer z = Integer.valueOf(123);
        Integer k = Integer.valueOf(123);
        System.out.println((z == k) + "->true");// (-127~128):true other：false

        Integer m = 123;
        Integer n = 123;
        System.out.println((m == n) + "->true");// (-127~128):true other：false

        //////////////////////////////////////////////
        String s1 = "aaa";
        String s2 = "aaa";
        String s3 = new String("aaa");
        System.out.println((s1 == s3) + "->false");
        System.out.println((s1 == s2) + "->true");

        String s4 = s1.intern();
        String s5 = s2.intern();
        System.out.println((s4 == s5) + "->true");

        /////////////////////////////////////////////
        Dog dog = new Dog("A");
        System.out.println(dog.getObjectAddress()); // Dog@4554617c
        func(dog);
        System.out.println(dog.getObjectAddress()); // Dog@4554617c
        System.out.println(dog.getName() + "->A");

        Dog dog2 = new Dog("A");
        func2(dog2);
        System.out.println(dog2.getName() + "->B");
    }

    private static void func(Dog dog) {
        System.out.println(dog.getObjectAddress()); // Dog@4554617c
        dog = new Dog("B");
        System.out.println(dog.getObjectAddress()); // Dog@74a14482
        System.out.println(dog.getName() + "->B");
    }

    private static void func2(Dog dog) {
        dog.setName("B");
    }

    static class Dog {
        String name;

        Dog(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        String getObjectAddress() {
            return super.toString();
        }
    }
}
