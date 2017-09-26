package com.zhl.huiqu.widget.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by dw on 2017/9/26.
 */

public class MyRecylerView extends RecyclerView {
    private GestureDetector gestureDetector;

    public MyRecylerView(Context context) {
        super(context);
    }

    public MyRecylerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecylerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setGestureDetector(GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        super.onTouchEvent(e);
        return gestureDetector.onTouchEvent(e);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        gestureDetector.onTouchEvent(ev);
        super.dispatchTouchEvent(ev);
        return true;
    }

}
