package com.qiugong.advance.x12;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author qzx 20/1/12.
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        DiskClassLoader diskClassLoader = new DiskClassLoader("D:\\learn\\Android\\CodeProject\\Book");
        try {
            Class c = diskClassLoader.loadClass("com.qiugong.advance.Jobs");
            if (c != null) {
                try {
                    Object obj = c.newInstance();
                    System.out.println(obj.getClass().getClassLoader());
                    Method method = c.getDeclaredMethod("say", null);
                    method.invoke(obj, null);
                } catch (InstantiationException | IllegalAccessException
                        | NoSuchMethodException
                        | SecurityException |
                        IllegalArgumentException |
                        InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
