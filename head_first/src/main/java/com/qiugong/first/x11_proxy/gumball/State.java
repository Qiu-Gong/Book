package com.qiugong.first.x11_proxy.gumball;

import java.io.Serializable;

public interface State extends Serializable {

    public void insertQuarter();

    public void ejectQuarter();

    public void turnCrank();

    public void dispense();

    public void refill();
}
