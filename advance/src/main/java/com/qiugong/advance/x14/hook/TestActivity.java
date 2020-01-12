package com.qiugong.advance.x14.hook;

import android.app.Activity;
import android.os.Bundle;

import com.qiugong.advance.R;

/**
 * @author qzx 20/1/12.
 */
public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
