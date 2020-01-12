package com.qiugong.advance.x14.purchasing;

/**
 * @author qzx 2019/8/27.
 */
public class Client {

    public static void main(String[] args) {
        IShop shop = new Myself();
        IShop purchasing = new Purchasing(shop);
        purchasing.buy();
    }
}
