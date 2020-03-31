package com.qiugong.skin_core;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * @author qzx 20/3/20.
 */
class SkinResources {

    private static SkinResources instance;

    private String mSkinPkgName;
    private Resources mAppResources;

    private boolean isDefaultSkin = true;
    private Resources mSkinResources;

    static void init(Context context) {
        if (instance == null) {
            synchronized (SkinResources.class) {
                if (instance == null) {
                    instance = new SkinResources(context);
                }
            }
        }
    }

    static SkinResources getInstance() {
        return instance;
    }

    private SkinResources(Context context) {
        mAppResources = context.getResources();
    }

    void reset() {
        mSkinResources = null;
        mSkinPkgName = "";
        isDefaultSkin = true;
    }

    void applySkin(Resources resources, String pkgName) {
        mSkinResources = resources;
        mSkinPkgName = pkgName;
        isDefaultSkin = TextUtils.isEmpty(pkgName) || resources == null;
    }

    int getColor(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColor(resId);
        }

        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColor(resId);
        }
        return mSkinResources.getColor(skinId);
    }

    ColorStateList getColorStateList(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColorStateList(resId);
        }

        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColorStateList(resId);
        }
        return mSkinResources.getColorStateList(skinId);
    }

    Drawable getDrawable(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getDrawable(resId);
        }

        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getDrawable(resId);
        }
        return mSkinResources.getDrawable(skinId);
    }

    Typeface getTypeface(int typefaceId) {
        String typefacePath = getString(typefaceId);
        if (TextUtils.isEmpty(typefacePath)) {
            return Typeface.DEFAULT;
        }

        if (isDefaultSkin) {
            return Typeface.createFromAsset(mAppResources.getAssets(), typefacePath);
        } else {
            return Typeface.createFromAsset(mSkinResources.getAssets(), typefacePath);
        }
    }

    public Object getBackground(int resId) {
        String resourceTypeName = mAppResources.getResourceTypeName(resId);
        if (resourceTypeName.equals("color")) {
            return getColor(resId);
        } else {
            return getDrawable(resId);
        }
    }

    private int getIdentifier(int resId) {
        if (isDefaultSkin) {
            return resId;
        }

        String resName = mAppResources.getResourceEntryName(resId);
        String resType = mAppResources.getResourceTypeName(resId);
        return mSkinResources.getIdentifier(resName, resType, mSkinPkgName);
    }

    private String getString(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getString(resId);
        }

        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getString(resId);
        }
        return mSkinResources.getString(skinId);
    }
}
