package com.kun.common.widge.viewpage;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 禁止左右滑动的ViewPage
 */
public class ViewPageCannotScroll extends ViewPager {
    private boolean noScroll = true;
    public ViewPageCannotScroll(@NonNull Context context) {
        super(context);
    }

    public ViewPageCannotScroll(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (noScroll){
            return false;}
        else{
            return super.onTouchEvent(arg0);}
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll){
            return false;}
        else{
            return super.onInterceptTouchEvent(arg0);}
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }
}
