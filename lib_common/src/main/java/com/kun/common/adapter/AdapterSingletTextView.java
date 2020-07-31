package com.kun.common.adapter;

import java.util.ArrayList;

import com.kun.common.R;
import com.kun.common.domain.ModelSingleText;
import com.kun.common.widge.layout.LayoutSingleLine;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Administrator on 2018/10/10.
 * 单TextView 适配器
 */

public class AdapterSingletTextView extends BaseAdapter {
  private Activity context;

  private ArrayList<ModelSingleText> datas;

  private LayoutInflater layoutInflater;

  public AdapterSingletTextView(Activity context, ArrayList<ModelSingleText> datas) {
    this.context = context;
    this.datas = datas;
    this.layoutInflater = LayoutInflater.from(context);
  }

  @Override
  public int getCount() {
    return datas.size();
  }

  @Override
  public Object getItem(int position) {
    return datas.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
    if (convertView == null) {
      viewHolder = new ViewHolder();
      convertView = layoutInflater.inflate(R.layout.mainlib_layout_single_line_layout, parent, false);
      viewHolder.layoutSingleLine = convertView.findViewById(R.id.mainlib_layout_layoutsingleline);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }
    ModelSingleText data = datas.get(position);
    viewHolder.layoutSingleLine.setTextViewLeft(data.getTitle());
    viewHolder.layoutSingleLine.setBigLeft();
    viewHolder.layoutSingleLine.ll_content.setClickable(false);
    if (data.isSelect()) {
      viewHolder.layoutSingleLine.tv_left.setTextColor(context.getResources().getColor(R.color.red));
    } else {
      viewHolder.layoutSingleLine.tv_left.setTextColor(context.getResources().getColor(R.color.gray));
    }
    return convertView;
  }

  class ViewHolder {
    private LayoutSingleLine layoutSingleLine;
  }
}
