package com.qiugong.first.x10_state.xx02_gumballstate.state;

public interface State {

    public void insertQuarter();

    public void ejectQuarter();

    public void turnCrank();

    public void dispense();

    public void refill();
}
