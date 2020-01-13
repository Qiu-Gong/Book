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

        /////////////////////////////////////////////
        A a = new A();
        B b = new B();
        C c = new C();
        D d = new D();
        a.show(a);// A.show(A)
        a.show(b);// A.show(A)
        b.show(c);// A.show(C)
        b.show(d);// A.show(C)

        A ba = new B();
        ba.show(c);// A.show(C)
        ba.show(d);// A.show(C)

        /////////////////////////////////////////////
        CloneExample e1 = new CloneExample();
        try {
            CloneExample e2 = e1.clone();
            e2.a = 10;
            System.out.println((e1.a == e2.a) + "->true");
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
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

    static class A {
        public void show(A obj) {
            System.out.println("A.show(A)");
        }

        public void show(C obj) {
            System.out.println("A.show(C)");
        }
    }

    static class B extends A {
        @Override
        public void show(A obj) {
            System.out.println("B.show(A)");
        }
    }

    static class C extends B {
    }

    static class D extends C {
    }

    static class CloneExample implements Cloneable {
        private int a = 2;
        private int b = 4;

        @Override
        public CloneExample clone() throws CloneNotSupportedException {
            System.out.println("clone");
            return (CloneExample) super.clone();
        }
    }
}
