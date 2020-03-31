package com.qiugong.skin_core;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qzx 20/3/20.
 */
class SkinAttribute {

    private static final String TAG = "SkinAttribute";
    private static final List<String> mAttributes = new ArrayList<>();

    static {
        mAttributes.add("background");
        mAttributes.add("src");
        mAttributes.add("textColor");
        mAttributes.add("drawableLeft");
        mAttributes.add("drawableTop");
        mAttributes.add("drawableRight");
        mAttributes.add("drawableBottom");
        mAttributes.add("skinTypeface");
    }

    private Typeface typeface;
    private List<SkinView> mSkinViews = new ArrayList<>();

    SkinAttribute(Typeface typeface) {
        this.typeface = typeface;
    }

    void load(View view, AttributeSet attrs) {
        List<SkinPair> mSkinPars = new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attributeName = attrs.getAttributeName(i);
            Log.d(TAG, attributeName);

            if (mAttributes.contains(attributeName)) {
                // 如果是color的 以#开头表示写死的颜色 不可用于换肤
                String attributeValue = attrs.getAttributeValue(i);
                if (attributeValue.startsWith("#")) {
                    continue;
                }

                int resId;
                if (attributeValue.startsWith("?")) {
                    int attrId = Integer.parseInt(attributeValue.substring(1));
                    resId = SkinTheme.getResId(view.getContext(), new int[]{attrId})[0];
                } else {
                    // 正常以 @ 开头
                    resId = Integer.parseInt(attributeValue.substring(1));
                }

                Log.d(TAG, attributeName + " = " + attributeValue);
                SkinPair skinPair = new SkinPair(attributeName, resId);
                mSkinPars.add(skinPair);
            }
        }

        if (!mSkinPars.isEmpty()) {
            SkinView skinView = new SkinView(view, mSkinPars);
            skinView.applySkin(typeface);
            mSkinViews.add(skinView);
        } else if (view instanceof TextView || view instanceof SkinViewSupport) {
            SkinView skinView = new SkinView(view, mSkinPars);
            skinView.applySkin(typeface);
            mSkinViews.add(skinView);
        }
    }

    void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    void applySkin() {
        for (SkinView mSkinView : mSkinViews) {
            mSkinView.applySkin(typeface);
        }
    }

    private static class SkinView {

        private View view;
        private List<SkinPair> skinPairs;

        SkinView(View view, List<SkinPair> skinPairs) {
            this.view = view;
            this.skinPairs = skinPairs;
        }

        void applySkin(Typeface typeface) {
            applyTypeFace(typeface);
            applySkinSupport();

            for (SkinPair skinPair : skinPairs) {
                Drawable left = null, top = null, right = null, bottom = null;
                switch (skinPair.attributeName) {
                    case "background":
                        Object background = SkinResources.getInstance().getBackground(skinPair.resId);
                        if (background instanceof Integer) {
                            view.setBackgroundColor((int) background);
                        } else {
                            ViewCompat.setBackground(view, (Drawable) background);
                        }
                        break;

                    case "src":
                        background = SkinResources.getInstance().getBackground(skinPair.resId);
                        if (background instanceof Integer) {
                            ((ImageView) view).setImageDrawable(new ColorDrawable((Integer) background));
                        } else {
                            ((ImageView) view).setImageDrawable((Drawable) background);
                        }
                        break;

                    case "textColor":
                        ((TextView) view).setTextColor(SkinResources.getInstance().getColorStateList(skinPair.resId));
                        break;

                    case "drawableLeft":
                        left = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;

                    case "drawableTop":
                        top = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;

                    case "drawableRight":
                        right = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;

                    case "drawableBottom":
                        bottom = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;

                    case "skinTypeface":
                        applyTypeFace(SkinResources.getInstance().getTypeface(skinPair.resId));
                        break;

                    default:
                        break;
                }

                if (null != left || null != right || null != top || null != bottom) {
                    ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
                }
            }
        }

        private void applySkinSupport() {
            if (view instanceof SkinViewSupport) {
                ((SkinViewSupport) view).applySkin();
            }
        }

        private void applyTypeFace(Typeface typeface) {
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(typeface);
            }
        }
    }

    private static class SkinPair {
        private String attributeName;
        private int resId;

        SkinPair(String attributeName, int resId) {
            this.attributeName = attributeName;
            this.resId = resId;
        }
    }
}
