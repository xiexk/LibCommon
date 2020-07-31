package com.kun.common.support.imageloader;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.kun.common.tools.ImageHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/11/21.
 */
public class MathWidthNoPadingListener implements ImageLoadingListener {
  private ImageView iv;

  private Context context;

  private int width;

  public MathWidthNoPadingListener(Context context, ImageView iv) {
    this.context = context;
    this.iv = iv;
  }

  public MathWidthNoPadingListener(Context context, ImageView iv, int width) {
    this.context = context;
    this.iv = iv;
    this.width = width;
  }

  @Override
  public void onLoadingStarted(String s, View view) {

  }

  @Override
  public void onLoadingFailed(String s, View view, FailReason failReason) {

  }

  @Override
  public void onLoadingComplete(String s, View view, Bitmap bitmap) {
    ImageHelper.setImageSizeFullwidth(context, iv, bitmap, width);
  }

  @Override
  public void onLoadingCancelled(String s, View view) {

  }
}