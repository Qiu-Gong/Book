package com.qiugong.skin_core;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;

/**
 * @author qzx 20/3/20.
 */
public class SkinTheme {

    private static int[] APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS = {
            android.R.attr.colorPrimaryDark
    };

    private static int[] STATUS_BAR_COLOR_ATTRS = {
            android.R.attr.statusBarColor,
            android.R.attr.navigationBarColor
    };

    private static int[] TYPEFACE_ATTRS = {
            R.attr.skinTypeface
    };

    static int[] getResId(Context context, int[] attrs) {
        int[] resId = new int[attrs.length];
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        for (int i = 0; i < typedArray.length(); i++) {
            resId[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
        return resId;
    }

    static void updateStatusBarColor(Activity activity) {
        // 5.0 以上修改
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        // 获得 statusBarColor 与 navigationBarColor (状态栏颜色)
        // 当与 colorPrimaryDark  不同时以 statusBarColor 为准
        int[] statusBarColorResId = getResId(activity, STATUS_BAR_COLOR_ATTRS);
        if (statusBarColorResId[0] != 0) {
            activity.getWindow()
                    .setStatusBarColor(SkinResources.getInstance().getColor(statusBarColorResId[0]));
        } else {
            int colorPrimaryDarkResId = getResId(activity, APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS)[0];
            if (colorPrimaryDarkResId != 0) {
                activity.getWindow()
                        .setStatusBarColor(SkinResources.getInstance().getColor(colorPrimaryDarkResId));
            }
        }

        if (statusBarColorResId[1] != 0) {
            activity.getWindow()
                    .setNavigationBarColor(SkinResources.getInstance().getColor(statusBarColorResId[1]));
        }
    }

    static Typeface getSkinTypeface(Activity activity) {
        int skinTypefaceId = getResId(activity, TYPEFACE_ATTRS)[0];
        return SkinResources.getInstance().getTypeface(skinTypefaceId);
    }
}
