package com.qiugong.advance.x14.dynamic;

import com.qiugong.advance.x14.purchasing.IShop;
import com.qiugong.advance.x14.purchasing.Myself;

import java.lang.reflect.Proxy;

/**
 * @author qzx 20/1/12.
 */
public class Client {
    public static void main(String[] args) {
        IShop self = new Myself();
        DynamicPurchasing dynamic = new DynamicPurchasing(self);

        ClassLoader loader = self.getClass().getClassLoader();
        IShop purchasing = (IShop) Proxy.newProxyInstance(loader, new Class[]{IShop.class}, dynamic);
        purchasing.buy();
    }
}
