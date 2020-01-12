package com.qiugong.advance.x14.purchasing;

/**
 * @author qzx 2019/8/27.
 */
public class Purchasing implements IShop {

    private IShop mShop;

    public Purchasing(IShop shop) {
        this.mShop = shop;
    }

    @Override
    public void buy() {
        this.mShop.buy();
    }
}
