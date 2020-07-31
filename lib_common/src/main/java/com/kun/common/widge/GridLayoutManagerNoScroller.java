package com.kun.common.widge;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.GridLayoutManager;

/**
 * Created by Administrator on 2019/1/27.
 * 取消滑动的GrideleLayout
 */


public class GridLayoutManagerNoScroller extends GridLayoutManager {
  private boolean isScrollEnabled = true;

  public GridLayoutManagerNoScroller(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public GridLayoutManagerNoScroller(Context context, int spanCount) {
    super(context, spanCount);
  }

  public GridLayoutManagerNoScroller(Context context, int spanCount, int orientation, boolean reverseLayout) {
    super(context, spanCount, orientation, reverseLayout);
  }

  public void setScrollEnabled(boolean flag) {
    this.isScrollEnabled = flag;
  }

  @Override
  public boolean canScrollVertically() {
    return isScrollEnabled && super.canScrollVertically();
  }
}
