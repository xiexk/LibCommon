package com.kun.common.widge.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2018/12/31.
 * 处理滑动监听的
 */

public class ObservableScrollView extends ScrollView {
  private ScrollViewListener scrollViewListener = null;

  public ObservableScrollView(Context context) {
    super(context);
  }

  public ObservableScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    if (scrollViewListener != null) {
      scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
    }
  }


  public void setScrollViewListener(ScrollViewListener scrollViewListener) {
    this.scrollViewListener = scrollViewListener;
  }

  public interface ScrollViewListener {
    void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
  }
}
