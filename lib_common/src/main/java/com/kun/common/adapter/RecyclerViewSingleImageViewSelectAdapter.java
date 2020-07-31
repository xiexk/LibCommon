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
 * 单个ImageView 的RecycleView的Adapter 可以选择的 添加图片等 包括“+”图片
 */

public class RecyclerViewSingleImageViewSelectAdapter extends
    RecyclerView.Adapter<RecyclerViewSingleImageViewSelectAdapter.ChildViewHolder> implements View.OnClickListener {
  private String imageAddUrl = "drawable://" + R.drawable.mainlib_image_add;

  private int maxCount;//实际图片数量

  public ArrayList<String> datas=new ArrayList<>();//实际图片列表 没有加号

  public ArrayList<String> datasHaveAdd=new ArrayList<>();//有加号

  public Activity context;

  public LayoutInflater layoutInflater;

  private OnClickImageViewListener onClickImageViewListener;

  private int rowcount = 3;//一行个数 默认三个

  private int padding = 20;

  private int spitSize = 0;//间距

  private boolean heightEquaWidth = false;//设置图片宽高是否相等

  private long tag;//区别其他相同的adapter


  public RecyclerViewSingleImageViewSelectAdapter(Activity context, ArrayList<String> datas) {
    this(context, datas, 3, 0, false, 9,0);
  }

  public RecyclerViewSingleImageViewSelectAdapter(Activity context, ArrayList<String> datas, int rowcount) {
    this(context, datas, rowcount, 0, false, 9,0);
  }

  public RecyclerViewSingleImageViewSelectAdapter(Activity context, ArrayList<String> datas, int rowcount,
      int spitSize) {
    this(context, datas, rowcount, spitSize, false, 9,0);
  }

  public RecyclerViewSingleImageViewSelectAdapter(Activity context, ArrayList<String> datas, int rowcount, int spitSize,
      int maxCount,long tag) {
    this(context, datas, rowcount, spitSize, false, maxCount,tag);
  }

  public RecyclerViewSingleImageViewSelectAdapter(Activity context, ArrayList<String> datas, int rowcount, int spitSize,
      boolean heightEquaWidth, int maxCount,long tag) {
    this.heightEquaWidth = heightEquaWidth;
    this.rowcount = rowcount;
    this.spitSize = spitSize;
    this.context = context;
    this.maxCount = maxCount;
    this.datas = datas;
    setDatasHaveAdd(datas);
    this.tag=tag;
    this.layoutInflater = LayoutInflater.from(context);
  }

  public void setDatas(ArrayList<String> datas) {
    this.datas = datas;
    setDatasHaveAdd(datas);
    notifyDataSetChanged();
  }

  private void setDatasHaveAdd(ArrayList<String> datas) {
    this.datasHaveAdd.clear();
    this.datasHaveAdd.addAll(datas);
    if (datas.size() < maxCount) {
      this.datasHaveAdd.add(imageAddUrl);
    }
  }


  @Override
  public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.mainlib_layout_single_image, null);
    return new ChildViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ChildViewHolder holder, int position) {
    String data = datasHaveAdd.get(position);
    if((data.contains("http://"))||(data.contains("https://"))||(data.contains("drawable://"))){

    }else {
      data="file://"+data;
    }

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
    return datasHaveAdd.size();
  }

  @Override
  public void onClick(View v) {
    int position = (int) v.getTag();

    if(position==datasHaveAdd.size()-1){//最后一个
      if(datas.size()==maxCount){//图片满了
        if (onClickImageViewListener != null) {
          onClickImageViewListener.clickImageView(tag,datas, position);//点击其他图片
        }
      }else {
        if (onClickImageViewListener != null) {
          onClickImageViewListener.clickAddImage(tag,maxCount-datas.size());//点击添加图片
        }
      }
    }else {
      if (onClickImageViewListener != null) {
        onClickImageViewListener.clickImageView(tag,datas, position);//点击其他图片
      }
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
    void clickAddImage(long tag,int canAddImageCount);

    void clickImageView(long tag,ArrayList<String> urls, int position);
  }

  public void setOnClickImageViewListener(OnClickImageViewListener onClickImageViewListener) {
    this.onClickImageViewListener = onClickImageViewListener;
  }
}
