package com.qiugong.skin_app.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.google.android.material.tabs.TabLayout;
import com.qiugong.skin_app.R;

/**
 * @author qzx 20/3/19.
 */
public class MyTabLayout extends TabLayout{

    private int tabIndicatorColorResId;
    private int tabTextColorResId;

    public MyTabLayout(Context context) {
        this(context, null, 0);
    }

    public MyTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint({"CustomViewStyleable", "PrivateResource"})
    public MyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabLayout, defStyleAttr, 0);
        tabIndicatorColorResId = a.getResourceId(R.styleable.TabLayout_tabIndicatorColor, 0);
        tabTextColorResId = a.getResourceId(R.styleable.TabLayout_tabTextColor, 0);
        a.recycle();
    }
}
