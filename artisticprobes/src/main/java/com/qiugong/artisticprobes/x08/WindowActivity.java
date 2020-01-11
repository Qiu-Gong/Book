package com.qiugong.artisticprobes.x08;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.qiugong.artisticprobes.R;

/**
 * @author qzx 2019/6/2.
 */
public class WindowActivity extends Activity implements View.OnTouchListener {

    private Button mFloatingButton;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);

        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        initView();
    }

    @Override
    protected void onDestroy() {
        mWindowManager.removeViewImmediate(mFloatingButton);
        super.onDestroy();
    }

    private void initView() {
        mFloatingButton = new Button(this);
        mFloatingButton.setText("123");
        mFloatingButton.setOnTouchListener(this);
        mLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                0, 0, PixelFormat.TRANSPARENT);
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        mLayoutParams.gravity = Gravity.START | Gravity.TOP;
        mLayoutParams.x = 100;
        mLayoutParams.y = 300;
        mWindowManager.addView(mFloatingButton, mLayoutParams);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                int x = (int) event.getX();
                int y = (int) event.getY();
                mLayoutParams.x = rawX;
                mLayoutParams.y = rawY;
                mWindowManager.updateViewLayout(mFloatingButton, mLayoutParams);
                break;
            }

            case MotionEvent.ACTION_UP: {
                break;
            }

            default:
                break;
        }

        return false;
    }
}
