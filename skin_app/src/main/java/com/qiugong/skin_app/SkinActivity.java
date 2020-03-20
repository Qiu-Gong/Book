package com.qiugong.skin_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * @author qzx 20/3/19.
 */
public class SkinActivity extends AppCompatActivity {

    private static final String TAG = "SkinActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);

        findViewById(R.id.mode_day).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NightModeConfig.getInstance().getNightMode(getApplicationContext())) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    NightModeConfig.getInstance().setNightMode(getApplicationContext(), false);
                    recreate();
                    Log.d(TAG, "日间模式");
                }
            }
        });

        findViewById(R.id.mode_night).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NightModeConfig.getInstance().getNightMode(getApplicationContext())) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    NightModeConfig.getInstance().setNightMode(getApplicationContext(), true);
                    recreate();
                    Log.d(TAG, "夜间模式");
                }
            }
        });
    }
}
