package com.kun.common.adapter;

import java.util.ArrayList;

import com.kun.common.R;
import com.kun.common.support.imageloader.MathWidthNoPadingListener;
import com.kun.common.tools.ImageHelper;
import com.kun.common.tools.PhoneHelper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Administrator on 2018/9/18.
 * 单个ImageView 的RecycleView的Adapter
 */

public class RecyclerViewSingleImageViewAdapter extends
    RecyclerView.Adapter<RecyclerViewSingleImageViewAdapter.ChildViewHolder> implements View.OnClickListener {

  public ArrayList<String> datas;

  public Activity context;

  public LayoutInflater layoutInflater;

  private OnClickImageViewListener onClickImageViewListener;

  private int rowcount = 3;//一行个数 默认三个

  private int padding = 20;

  private int spitSize = 0;//间距

  private boolean heightEquaWidth = false;//设置图片宽高是否相等


  public RecyclerViewSingleImageViewAdapter(Activity context, ArrayList<String> datas) {
    this(context, datas, 3, 0, false);
  }

  public RecyclerViewSingleImageViewAdapter(Activity context, ArrayList<String> datas, int rowcount) {
    this(context, datas, rowcount, 0, false);
  }

  public RecyclerViewSingleImageViewAdapter(Activity context, ArrayList<String> datas, int rowcount, int spitSize) {
    this(context, datas, rowcount, spitSize, false);
  }

  public RecyclerViewSingleImageViewAdapter(Activity context, ArrayList<String> datas, int rowcount, int spitSize,
      boolean heightEquaWidth) {
    this.heightEquaWidth = heightEquaWidth;
    this.rowcount = rowcount;
    this.spitSize = spitSize;
    this.context = context;
    this.datas = datas;
    this.layoutInflater = LayoutInflater.from(context);
  }

  public void setDatas(ArrayList<String> datas) {
    this.datas = datas;
    notifyDataSetChanged();
  }

  @Override
  public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.mainlib_layout_single_image, null);
    return new ChildViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ChildViewHolder holder, int position) {
    String data = datas.get(position);

    int screenWidth = PhoneHelper.SCREEN_WIDTH;
    int iv_width = (screenWidth - PhoneHelper.dip2px(context, ((padding * 2) + (spitSize * rowcount)))) / rowcount;
    if (heightEquaWidth) {
      ImageHelper.getImageLoader().displayImage(data, holder.iv, ImageHelper.getDisplayImageOptions());
      ViewGroup.LayoutParams params = holder.iv.getLayoutParams();
      params.width = iv_width;
      params.height = iv_width;
    } else {
      ImageHelper.getImageLoader().displayImage(data, holder.iv, ImageHelper.getDisplayImageOptions(),
          new MathWidthNoPadingListener(context, holder.iv, iv_width));
    }
    holder.iv.setScaleType(ImageView.ScaleType.FIT_XY);
    holder.iv.setTag(position);
    holder.iv.setOnClickListener(this);
  }

  @Override
  public int getItemCount() {
    return datas.size();
  }

  @Override
  public void onClick(View v) {
    int position = (int) v.getTag();

    if (onClickImageViewListener != null) {
      onClickImageViewListener.clickImageView(datas, position);
    }
  }

  public class ChildViewHolder extends RecyclerView.ViewHolder {

    ImageView iv;

    ChildViewHolder(View view) {
      super(view);
      iv = view.findViewById(R.id.mainlib_item_single_image_iv);
    }
  }

  public interface OnClickImageViewListener {
    void clickImageView(ArrayList<String> urls, int position);
  }

  public void setOnClickImageViewListener(OnClickImageViewListener onClickImageViewListener) {
    this.onClickImageViewListener = onClickImageViewListener;
  }
}
