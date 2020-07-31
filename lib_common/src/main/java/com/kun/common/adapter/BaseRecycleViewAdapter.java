package com.kun.common.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Administrator on 2018/10/18.
 * BaseRecycleViewAdapter 处理通用的 data数据 Activity 和LayotInflater
 */

public abstract class BaseRecycleViewAdapter<T, K extends BaseChildHolder> extends RecyclerView.Adapter<K> {

  public ArrayList<T> datas = new ArrayList<>();

  public Activity context;

  public LayoutInflater layoutInflater;

  public BaseRecycleViewAdapter(Activity context, ArrayList<T> datas) {
    this.context = context;
    this.datas = datas;
    this.layoutInflater = LayoutInflater.from(context);
  }

  public void setDatas(ArrayList<T> datas) {
    this.datas = datas;
    notifyDataSetChanged();
  }


  @Override
  public int getItemCount() {
    return datas.size();
  }
}
