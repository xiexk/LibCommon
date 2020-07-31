package com.kun.common.widge;

import com.daimajia.swipe.SwipeLayout;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Administrator on 2018/9/11.
 * 处理左滑和viewpage冲突
 */

public class ViewPageCanScroll extends ViewPager {

  public ViewPageCanScroll(@NonNull Context context) {
    super(context);
  }

  public ViewPageCanScroll(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {

    return super.canScroll(v, checkV, dx, x, y) || (v instanceof SwipeLayout);
  }
}
