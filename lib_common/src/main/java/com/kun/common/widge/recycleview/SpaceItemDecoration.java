package com.kun.common.widge.recycleview;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Administrator on 2018/9/19.
 * RecycleView边距
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

  private int space; //纵向间距

  private int horizon;//横向间距 同同时设置左边和右边 为了对称

  private boolean firstLineShow = true;//第一行的分割线显示

  public SpaceItemDecoration(int space) {
    this.space = space;
  }

  public SpaceItemDecoration(int space, boolean firstLineShow) {
    this.space = space;
    this.firstLineShow = firstLineShow;
  }

  public SpaceItemDecoration(int space, int horizon) {
    this.space = space;
    this.horizon = horizon;
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    int pos = parent.getChildLayoutPosition(view);  //当前条目的position
    int itemCount = state.getItemCount() - 1;           //最后一条的postion

    outRect.top = space;
    // outRect.bottom = space;
    outRect.left = horizon;
    outRect.right = horizon;
    //第一行分割线不显示
    if (!firstLineShow) {
      if (pos == 0) {
        outRect.top = 0;
      }
    }
  }
}