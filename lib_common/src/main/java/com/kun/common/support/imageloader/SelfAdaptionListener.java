package com.kun.common.support.imageloader;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.kun.common.tools.ImageHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;


/**
 * Created by Administrator on 2016/9/28.
 * 设置高度自适应宽度的imageLoder监听
 */
public class SelfAdaptionListener implements ImageLoadingListener {
  private ImageView iv;

  private int height;

  private Context context;

  public SelfAdaptionListener(Context context, ImageView iv, int height) {
    this.context = context;
    this.iv = iv;
    this.height = height;
  }

  @Override
  public void onLoadingStarted(String s, View view) {

  }

  @Override
  public void onLoadingFailed(String s, View view, FailReason failReason) {

  }

  @Override
  public void onLoadingComplete(String s, View view, Bitmap bitmap) {
    ImageHelper.setBgImageHeight(context, iv, height, bitmap, true);
  }

  @Override
  public void onLoadingCancelled(String s, View view) {

  }
}
