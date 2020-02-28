package com.qiugong.tinker;

import android.content.Context;
import android.widget.Toast;

class Calculator {

    void calculate(Context context) {
        int a = 666;
        int b = 0;
        Toast.makeText(context, "calculate >>> " + a / b, Toast.LENGTH_SHORT).show();
    }
}
