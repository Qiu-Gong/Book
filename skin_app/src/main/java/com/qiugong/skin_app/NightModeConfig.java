package com.qiugong.skin_app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author qzx 20/3/19.
 */
class NightModeConfig {

    private static final String NIGHT_MODE = "sp_night_mode";
    private static final String IS_NIGHT_MODE = "is_night_mode";

    private static NightModeConfig sModeConfig;
    private SharedPreferences mSharedPreference;

    static NightModeConfig getInstance() {
        if (sModeConfig == null) {
            sModeConfig = new NightModeConfig();
        }
        return sModeConfig;
    }

    boolean getNightMode(Context context) {
        if (mSharedPreference == null) {
            mSharedPreference = context.getSharedPreferences(NIGHT_MODE, Context.MODE_PRIVATE);
        }
        return mSharedPreference.getBoolean(IS_NIGHT_MODE, false);
    }

    public void setNightMode(Context context, boolean isNightMode) {
        if (mSharedPreference == null) {
            mSharedPreference = context.getSharedPreferences(NIGHT_MODE, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putBoolean(IS_NIGHT_MODE, isNightMode);
        editor.apply();
    }
}
