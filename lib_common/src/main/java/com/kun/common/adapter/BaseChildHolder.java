package com.kun.common.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Administrator on 2018/10/18.
 * RecycleViewAdapter 基础ChilderHolder
 */

public class BaseChildHolder extends RecyclerView.ViewHolder {
  public View baseView;

  public BaseChildHolder(View itemView) {
    super(itemView);
    this.baseView = itemView;
  }
}
