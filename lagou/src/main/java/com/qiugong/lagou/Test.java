package com.qiugong.lagou;

import java.io.Serializable;

public class Test implements Serializable, Cloneable {

    private int num = 1;

    public int add(int i) {
        int j = 10;
        num = num + i;
        return num;
    }
}

