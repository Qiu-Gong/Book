package com.qiugong.skin_core;

import android.content.Context;
import android.content.SharedPreferences;

class SkinPreference {

    private static final String SKIN_SHARED = "sp_skins";
    private static final String KEY_SKIN_PATH = "skin_path";

    private static SkinPreference instance;
    private final SharedPreferences mSharedPreferences;

    static void init(Context context) {
        if (instance == null) {
            synchronized (SkinPreference.class) {
                if (instance == null) {
                    instance = new SkinPreference(context.getApplicationContext());
                }
            }
        }
    }

    static SkinPreference getInstance() {
        return instance;
    }

    private SkinPreference(Context context) {
        mSharedPreferences = context.getSharedPreferences(SKIN_SHARED, Context.MODE_PRIVATE);
    }

    void setSkinPath(String skinPath) {
        mSharedPreferences.edit().putString(KEY_SKIN_PATH, skinPath).apply();
    }

    String getSkinPath() {
        return mSharedPreferences.getString(KEY_SKIN_PATH, null);
    }
}
