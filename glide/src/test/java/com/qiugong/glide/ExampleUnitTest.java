package com.qiugong.glide;

import org.junit.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

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
}