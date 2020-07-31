package com.kun.common.widge.layout;

import com.kun.common.R;
import com.kun.common.widge.textview.TextViewIconFont;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/8/30.
 * 自定义main头部RelaytiveLayout
 * 添加监听一定要在activity 或 fragment的添加监听模块添加
 */

public class LayoutMainHeader extends RelativeLayout implements View.OnClickListener {
  private TextView tv_title;

  private RelativeLayout rl_base, rl_search;

  public LinearLayout ll_back;

  public ClickLeftImageViewListener clickLeftImageViewListener;

  private ClickTextViewFontRight1Listener clickTextViewFontRight1Listener;

  private ClickTextViewFontRight2Listener clickImageButtonRight2Listener;

  private ClickSearchListener clickSearchListener;

  public TextViewIconFont tvf_right1, tvf_right2;

  public ImageView iv_search, iv_clean;

  public TextViewIconFont tvf_left;

  public EditText et_search;


  public LayoutMainHeader(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView(context, attrs);
    initData();
    initListener();
  }

  private void initView(Context context, AttributeSet attrs) {
    rl_base = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.mainlib_layout_head_main, this);
    tv_title = findViewById(R.id.mainlib_layout_main_head_iv_search_tv_title);
    ll_back = findViewById(R.id.mainlib_layout_main_head_back_layout);
    tvf_right1 = findViewById(R.id.mainlib_layout_main_head_image_btn_right1);
    tvf_right2 = findViewById(R.id.mainlib_layout_main_head_image_btn_right2);
    rl_search = findViewById(R.id.mainlib_layout_main_head_rl_search);
    iv_search = findViewById(R.id.mainlib_layout_main_head_iv_search);
    iv_clean = findViewById(R.id.mainlib_layout_main_head_iv_delete);
    et_search = findViewById(R.id.mainlib_layout_main_head_et_search);
    tvf_left = findViewById(R.id.mainlib_layout_main_head_tv_left);
  }

  private void initData() {
  }

  private void initListener() {
    ll_back.setOnClickListener(this);
    tvf_right1.setOnClickListener(this);
    tvf_right2.setOnClickListener(this);
    iv_search.setOnClickListener(this);
    iv_clean.setOnClickListener(this);
  }

  public void setTitle(String strTitle) {
    this.tv_title.setText(strTitle);
    this.tv_title.setVisibility(VISIBLE);
  }

  public void searchSetPadding(int marginLeft) {
    RelativeLayout.LayoutParams params = (LayoutParams) rl_search.getLayoutParams();
    params.setMargins(marginLeft, 0, 0, 0);
    rl_search.setLayoutParams(params);
  }

  /**
   * 隐藏返回按钮
   */
  public void hideBack() {
    ll_back.setVisibility(GONE);
  }

  public void setLocalBackGroundColor(int id) {
    this.rl_base.setBackgroundColor(getResources().getColor(id));
  }

  //设置左侧图片
  public void setLeftImageViewListener(ClickLeftImageViewListener clickLeftImageViewListener) {
    this.clickLeftImageViewListener = clickLeftImageViewListener;
  }

  //设置左侧图片 包括监听
  public void setLeftImageView(String id, ClickLeftImageViewListener clickLeftImageViewListener) {
    this.tvf_left.setText(id);
    this.clickLeftImageViewListener = clickLeftImageViewListener;
  }


  //设置右侧按钮1 包括监听
  public void setTextViewFontRight1(String id, ClickTextViewFontRight1Listener clickTextViewFontRight1Listener) {
    setTextViewFontRight1(id, clickTextViewFontRight1Listener, 0);
  }

  //设置右侧按钮1 设置文字大小 包括监听 （setTextViewFontRight2("说说", this, 15);）15就是dp
  public void setTextViewFontRight1(String id, ClickTextViewFontRight1Listener clickTextViewFontRight1Listener,
      int textSize) {
    if (textSize != 0) {
      this.tvf_right1.setTextSize(textSize);
    }
    this.tvf_right1.setText(id);
    this.tvf_right1.setVisibility(VISIBLE);
    this.clickTextViewFontRight1Listener = clickTextViewFontRight1Listener;
  }

  //设置右侧按钮end 包括监听
  public void setTextViewFontRight2(String id, ClickTextViewFontRight2Listener clickImageButtonRight2Listener) {
    setTextViewFontRight2(id, clickImageButtonRight2Listener, 0);
  }

  //设置右侧按钮end 包括监听 设置文字大小  （setTextViewFontRight2("说说", this, 15);）15就是dp
  public void setTextViewFontRight2(String id, ClickTextViewFontRight2Listener clickImageButtonRight2Listener,
      int textSize) {
    if (textSize != 0) {
      this.tvf_right2.setTextSize(textSize);
    }
    this.tvf_right2.setText(id);
    this.tvf_right2.setVisibility(VISIBLE);
    this.clickImageButtonRight2Listener = clickImageButtonRight2Listener;
  }

  public void setSearchListener(ClickSearchListener clickSearchListener) {
    if (clickSearchListener == null) {
      this.rl_search.setVisibility(GONE);
      this.iv_search.setVisibility(GONE);
      this.clickSearchListener = null;
    } else {
      this.rl_search.setVisibility(VISIBLE);
      this.iv_search.setVisibility(VISIBLE);
      this.clickSearchListener = clickSearchListener;
    }
  }

  //获取输入框内容
  public String getEditTextStr() {
    return et_search.getText().toString();
  }


  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.mainlib_layout_main_head_back_layout) {
      if (clickLeftImageViewListener != null) {
        clickLeftImageViewListener.clickBack();
      }
    } else if (id == R.id.mainlib_layout_main_head_image_btn_right1) {
      if (clickTextViewFontRight1Listener != null) {
        clickTextViewFontRight1Listener.clickTextViewFontRight1();
      }
    } else if (id == R.id.mainlib_layout_main_head_image_btn_right2) {
      if (clickImageButtonRight2Listener != null) {
        clickImageButtonRight2Listener.clickTextViewFontRight2();
      }
    } else if (id == R.id.mainlib_layout_main_head_iv_delete) {//清除输入
      et_search.setText("");
    } else if (id == R.id.mainlib_layout_main_head_iv_search) {
      if (clickSearchListener != null) {
        clickSearchListener.clickSearchRight();
      }
    }
  }

  public void setClickLeftImageViewListener(ClickLeftImageViewListener clickLeftImageViewListener) {
    this.clickLeftImageViewListener = clickLeftImageViewListener;
  }

  public interface ClickLeftImageViewListener {
    void clickBack();
  }

  public interface ClickTextViewFontRight1Listener {
    void clickTextViewFontRight1();
  }

  public interface ClickTextViewFontRight2Listener {
    void clickTextViewFontRight2();
  }

  public interface ClickSearchListener {
    void clickSearchRight();
  }
}
