package com.kun.common.widge.layout;

import com.kun.common.R;
import com.kun.common.tools.LayoutHelper;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/10/19.
 * 标题内容layout 包含左侧图标 右侧右箭头按钮 中间是标题和内容
 */

public class LayoutTitleContent extends LinearLayout {
  public ImageView iv_left, iv_right;

  public TextView tv_title, tv_content;

  public LinearLayout ll_content;

  public LayoutTitleContent(Context context) {
    super(context);
    init(context);
  }

  public LayoutTitleContent(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public LayoutTitleContent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    ll_content = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.mainlib_layout_title_content, this);
    iv_left = findViewById(R.id.mainlib_layout_title_content_iv_left);
    iv_right = findViewById(R.id.mainlib_layout_title_content_iv_right);
    tv_title = findViewById(R.id.mainlib_layout_title_content_tv_title);
    tv_content = findViewById(R.id.mainlib_layout_title_content_tv_content);
  }

  public void setImageLeft(int id) {
    setImageView(iv_left, id, 22, 22);
  }

  public void setImageRight(int id) {
    setImageView(iv_right, id, 22, 22);
  }

  public void setTextViewTitle(String str) {
    setTextView(tv_title, str);
  }

  public void setTextViewContent(String str) {
    setTextView(tv_content, str);
  }

  private void setImageView(ImageView iv, int resourceId, int width, int height) {
    iv.setVisibility(VISIBLE);
    iv.setImageResource(resourceId);
    iv.setLayoutParams(LayoutHelper.getLinearLayoutLayoutParaments(getContext(), width, height));
  }

  private void setTextView(TextView tv, String str) {
    tv.setText(str);
    tv.setVisibility(VISIBLE);
  }

  public void setOnContentClickListener(OnClickListener onLayoutClickListener) {
    this.ll_content.setOnClickListener(onLayoutClickListener);
  }
}
