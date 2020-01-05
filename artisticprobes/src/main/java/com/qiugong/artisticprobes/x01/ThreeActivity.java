package com.qiugong.artisticprobes.x01;

import android.os.Bundle;
import android.widget.TextView;

import com.qiugong.artisticprobes.R;

public class ThreeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TextView) findViewById(R.id.text)).setText("ThreeActivity");
    }
}
