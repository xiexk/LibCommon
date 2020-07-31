package com.kun.common.widge.layout;


import com.kun.common.R;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/9/9.
 * 左右布局 类似购买底部按钮 的底部布局
 */

public class LayoutFooterCommon extends LinearLayout implements CompoundButton.OnCheckedChangeListener,
    View.OnClickListener {
  private TextView tv_left, tv_center1, tv_center2;

  private Button bt_right;

  public CheckBox cb_left;

  private OnSelectAllListener onSelectAllListener;

  private OnFooterButtonRightClickListener onFooterButtonRightClickListener;

  public LayoutFooterCommon(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initView(context);
    initData();
    initListener();
  }

  private void initView(Context context) {
    LayoutInflater.from(context).inflate(R.layout.mainlib_layout_footer_common, this);
    tv_left = findViewById(R.id.mainlib_layout_footer_common_tv_left);
    tv_center1 = findViewById(R.id.mainlib_layout_footer_common_tv_center1);
    tv_center2 = findViewById(R.id.mainlib_layout_footer_common_tv_center2);
    bt_right = findViewById(R.id.mainlib_layout_footer_common_bt_right);
    cb_left = findViewById(R.id.mainlib_layout_footer_common_checkbox);
    setLayoutParams(
        new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
  }

  public void setTextViewLeft(String str) {
    tv_left.setText(str);
    tv_left.setVisibility(VISIBLE);
    cb_left.setVisibility(GONE);
  }

  public void setButtonRightText(String str) {
    bt_right.setText(str);
  }

  public void setTextViewCenter1(String strCenter1) {
    tv_center1.setText(strCenter1);
    tv_center1.setVisibility(VISIBLE);
  }

  public void setTextViewCenter2(String strCenter2) {
    tv_center2.setText(strCenter2);
    tv_center2.setVisibility(VISIBLE);
  }

  private void initData() {

  }

  private void initListener() {
    this.cb_left.setOnCheckedChangeListener(this);
    this.bt_right.setOnClickListener(this);
  }

  @Override
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    if (onSelectAllListener != null) {
      onSelectAllListener.selectAll(isChecked);
    }
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.mainlib_layout_footer_common_bt_right) {
      if (onFooterButtonRightClickListener != null) {
        onFooterButtonRightClickListener.onFooterButtonRight();
      }
    }
  }

  public interface OnSelectAllListener {
    void selectAll(boolean selectAll);
  }

  public void setOnSelectAllListener(OnSelectAllListener onSelectAllListener) {
    this.onSelectAllListener = onSelectAllListener;
  }

  public interface OnFooterButtonRightClickListener {
    void onFooterButtonRight();
  }

  public void setOnFooterButtonRightClickListener(OnFooterButtonRightClickListener onFooterButtonRightClickListener) {
    this.onFooterButtonRightClickListener = onFooterButtonRightClickListener;
  }
}
