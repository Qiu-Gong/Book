package com.qiugong.skin_app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.qiugong.skin_app.R;

public class CircleView extends View {

    private Paint mTextPain;
    private int circleColorResId;

    public CircleView(Context context) {
        this(context, null, 0);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        circleColorResId = typedArray.getResourceId(R.styleable.CircleView_circleColor, 0);
        typedArray.recycle();

        mTextPain = new Paint();
        mTextPain.setColor(getResources().getColor(circleColorResId));
        mTextPain.setAntiAlias(true);
        mTextPain.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        int radius = Math.min(width, height);
        canvas.drawCircle(width, height, radius, mTextPain);
    }

    public void setCircleColor(int color) {
        mTextPain.setColor(color);
        invalidate();
    }
}
