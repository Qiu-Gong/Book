package com.qiugong.advance.x14.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author qzx 20/1/12.
 */
public class DynamicPurchasing implements InvocationHandler {

    private Object object;

    public DynamicPurchasing(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("buy")){
            System.out.println("加点东西");
        }
        return method.invoke(object, args);
    }
}
