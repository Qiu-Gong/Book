package com.qiugong.glide;

import org.junit.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testWeakReference() {
        final ReferenceQueue<String> queue = new ReferenceQueue<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Reference<? extends String> remove = queue.remove();
                    System.out.println("回收:" + remove);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        WeakReference<String> weak1 = new WeakReference<>(new String("1"), queue);
        System.out.println("weak 1:" + weak1.get() + " -> 1");

        String str = new String("2");
        WeakReference<String> weak2 = new WeakReference<>(str, queue);
        System.out.println("weak 2:" + weak2.get() + " -> 2");
        str = null;
        System.out.println("weak 2:" + str + " -> null");
        System.out.println("weak 2:" + weak2.get() + " -> 2");

        System.gc();
        System.out.println("weak 1:" + weak1.get() + " -> null");
        System.out.println("weak 2:" + str + " -> null");
        System.out.println("weak 2:" + weak2.get() + " -> null");

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class A {
    }

    private class B {
        final A a;

        B(A a) {
            this.a = a;
        }
    }

    @Test
    public void testABReference() {
        // B 持有 A，当 B 释放了，A 需要置空才能释放。
        A a = new A();
        System.out.println("a = " + a + " -> @");
        System.out.println("-------------------------------");

        B b = new B(a);
        System.out.println("a = " + a + " -> @");
        System.out.println("b = " + b + " -> @");
        System.out.println("-------------------------------");

        b = null;
        System.out.println("a = " + a + " -> @");
        System.out.println("b = " + b + " -> null");
        System.out.println("-------------------------------");

        System.gc();
        System.out.println("a = " + a + " -> @");
        System.out.println("b = " + b + " -> null");
        System.out.println("-------------------------------");
    }

    @Test
    public void testABWeakReference() {
        // B 持有 弱A，当 B 释放了，gc 后 弱A 释放。
        WeakReference<A> weak = new WeakReference<>(new A());
        System.out.println("a = " + weak.get() + " -> @");
        System.out.println("-------------------------------");

        B b = new B(weak.get());
        System.out.println("a = " + weak.get() + " -> @");
        System.out.println("b = " + b + " -> @");
        System.out.println("-------------------------------");

        b = null;
        System.out.println("a = " + weak.get() + " -> @");
        System.out.println("b = " + b + " -> null");
        System.out.println("-------------------------------");

        System.gc();
        System.out.println("a = " + weak.get() + " -> null");
        System.out.println("b = " + b + " -> null");
    }

    @Test
    public void testWeakHashMap() {
        WeakHashMap<String, String> weakHashMap = new WeakHashMap<>(10);

        String key0 = new String("kuang");
        String key1 = new String("zhong");
        String key2 = new String("wen");
        // 存放元素
        weakHashMap.put(key0, "q1");
        weakHashMap.put(key1, "q2");
        weakHashMap.put(key2, "q3");
        System.out.printf("weakHashMap: %s\n", weakHashMap);
        // 是否包含某key
        System.out.printf("contains key kuang : %s\n", weakHashMap.containsKey(key0));
        System.out.printf("contains key zhong : %s\n", weakHashMap.containsKey(key1));
        // 是否包含某value
        System.out.printf("contains value 0 : %s\n", weakHashMap.containsValue(0));
        // 移除key
        weakHashMap.remove(key2);
        System.out.printf("weakHashMap after remove: %s\n", weakHashMap);
        // 置空
        key0 = null;
        System.gc();
        System.out.printf("weakHashMap: %s\n", weakHashMap);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}