package com.kun.common.widge.layout;

import com.kun.common.R;
import com.kun.common.support.imageloader.SelfAdaptionListener;
import com.kun.common.tools.ImageHelper;
import com.kun.common.tools.LayoutHelper;
import com.kun.common.tools.PhoneHelper;
import com.kun.common.widge.textview.TextViewIconFont;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2018/9/3.
 * 单行通用textView，EditText
 *
 * 使用：
 *   <com.ud361.mainlib.widge.layout.LayoutSingleLine
 android:layout_width="match_parent"
 android:layout_height="wrap_content"
 layoutsingleline:tvleft="个人信息"
 layoutsingleline:tvcenter="中间文字"
 layoutsingleline:etcenterhint="中间编辑框hint内容
 layoutsingleline:ivleft="@drawable/commonresourcelib_next_gray_24dp" 左侧图标
 layoutsingleline:ivrightend="@drawable/commonresourcelib_next_gray_24dp" 右侧末尾图标
 layoutsingleline:ivrightfirst="@drawable/commonresourcelib_next_gray_24dp" 右侧图标
 layoutsingleline:ivcenter="@drawable/commonresourcelib_next_gray_24dp" 中间图标
 layoutsingleline:heightWrapContent="true" 高度自适应
 layoutsingleline:bigcenternoright="true" 中间最大化没有右侧内容
 layoutsingleline:showLine="true" 显示下划线

 */

public class LayoutSingleLine extends LinearLayout {

  private ImageView iv_left, iv_center, iv_right_first, iv_right_end;

  public TextViewIconFont tv_left, tv_center, tv_right;

  public EditText et_center;

  public LinearLayout ll_base, ll_content, ll_left, ll_center, ll_right;

  private View v_line;

  private Button bt_right;

  private String strleft, strCenter, strEtHint, strRight;

  private Drawable drawableLeft, drawableCenter, drawableRightFirst, drawableRightEnd;

  private boolean heightWrapContent=false;//高度自适应

  private boolean showLine=true;//显示下划线

  private boolean bigCenterNoRight;//


  public LayoutSingleLine(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initView(context, attrs);
  }

  private void initView(Context context, AttributeSet attrs) {

    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.layoutsingleline);
    strleft = typedArray.getString(R.styleable.layoutsingleline_tvleft);
    strCenter = typedArray.getString(R.styleable.layoutsingleline_tvcenter);
    strRight = typedArray.getString(R.styleable.layoutsingleline_tvright);
    strEtHint = typedArray.getString(R.styleable.layoutsingleline_etcenterhint);
    drawableLeft = typedArray.getDrawable(R.styleable.layoutsingleline_ivleft);
    drawableCenter = typedArray.getDrawable(R.styleable.layoutsingleline_ivcenter);
    drawableRightFirst = typedArray.getDrawable(R.styleable.layoutsingleline_ivrightfirst);
    drawableRightEnd = typedArray.getDrawable(R.styleable.layoutsingleline_ivrightend);
    heightWrapContent = typedArray.getBoolean(R.styleable.layoutsingleline_heightWrapContent,false);
    bigCenterNoRight = typedArray.getBoolean(R.styleable.layoutsingleline_bigcenternoright,false);
    showLine = typedArray.getBoolean(R.styleable.layoutsingleline_showLine,true);

    ll_base = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.mainlib_layout_single_line, this);

    iv_left = findViewById(R.id.mainlib_layout_single_line_iv_left);
    iv_center = findViewById(R.id.mainlib_layout_single_line_iv_center);
    iv_right_first = findViewById(R.id.mainlib_layout_single_line_iv_right_first);
    iv_right_end = findViewById(R.id.mainlib_layout_single_line_iv_right_end);

    tv_left = findViewById(R.id.mainlib_layout_single_line_tv_left);
    tv_center = findViewById(R.id.mainlib_layout_single_line_tv_center);
    tv_right = findViewById(R.id.mainlib_layout_single_line_tv_right);

    et_center = findViewById(R.id.mainlib_layout_single_line_et_center);
    bt_right = findViewById(R.id.mainlib_layout_single_line_bt_tight);

    v_line = findViewById(R.id.mainlib_layout_single_line_view_line);

    ll_left = findViewById(R.id.mainlib_layout_single_line_ll_left);
    ll_center = findViewById(R.id.mainlib_layout_single_line_ll_center);
    ll_right = findViewById(R.id.mainlib_layout_single_line_ll_right);

    ll_content = findViewById(R.id.mainlib_layout_single_line_ll_content);

    if (strleft != null) {
      setTextViewLeft(strleft);
    }
    if (strCenter != null) {
      setTextViewCenter(strCenter);
    }
    if (strRight != null) {
      setTextViewRight(strRight);
    }
    if (strEtHint != null) {
      setEditTextCenterHint(strEtHint);
    }
    if (drawableLeft != null) {
      setImageViewLeft(drawableLeft);
    }
    if (drawableCenter != null) {
      setImageViewCenter(drawableCenter);
    }
    if (drawableRightFirst != null) {
      setImageViewRightFirst(drawableRightFirst);
    }
    if (drawableRightEnd != null) {
      setImageViewRightEnd(drawableRightEnd);
    }
    if (heightWrapContent ) {
      heightWrapContent();
    }
    if(bigCenterNoRight){
      setBigCenterNoRight();
    }
     showLine(showLine);
  }



  /**
   * 设置左侧图片
   */
  public void setImageViewLeft(int resourceId) {
    setImageView(iv_left, resourceId, 22, 22);
    //设置左侧文字间距 （如果左侧文字是显示的状态 设置左侧文字左侧距离左侧图片的间距）
    checkMargin(tv_left, true);
  }


  /**
   * 设置左侧图片
   */
  public void setImageViewLeft(String imageUrl) {
    iv_left.setVisibility(VISIBLE);
    ImageHelper.getImageLoader().displayImage(imageUrl, iv_left,
        new SelfAdaptionListener(getContext(), iv_left, PhoneHelper.dip2px(getContext(), 16)));
    //设置左侧文字间距 （如果左侧文字是显示的状态 设置左侧文字左侧距离左侧图片的间距）
    checkMargin(tv_left, true);
  }


  /**
   * 设置左侧图片
   */
  public void setImageViewLeft(Drawable drawable) {
    iv_left.setVisibility(VISIBLE);
    setImageView(iv_left, drawable, 22, 22);
    //设置左侧文字间距 （如果左侧文字是显示的状态 设置左侧文字左侧距离左侧图片的间距）
    checkMargin(tv_left, true);
  }

  /**
   * 设置左侧文字
   */
  public void setTextViewLeft(String str) {
    setTextView(tv_left, str);
    checkMargin(iv_left, false);
  }
 /**
   * 设置左侧文字 并设置字体加粗
   */
  public void setTextViewLeft(String str,boolean typefaceBold) {
    setTextView(tv_left, str,typefaceBold);
    checkMargin(iv_left, false);
  }

  /**
   * 设置中间图片
   */
  public void setImageViewCenter(int resourceId) {
    setImageView(iv_center, resourceId, 22, 22);
    //设置中间文字边界 （如果中间文字是显示的状态 设置中间文字左侧距离中间图片的间距）
    checkMargin(tv_center, true);
  }

  /**
   * 设置中间图片
   */
  public void setImageViewCenter(Drawable drawable) {
    setImageView(iv_center, drawable, 22, 22);
    //设置中间文字边界 （如果中间文字是显示的状态 设置中间文字左侧距离中间图片的间距）
    checkMargin(tv_center, true);
  }

  /**
   * 设置中间文字
   */
  public void setTextViewCenter(String str) {
    setTextView(tv_center, str);
    //设置中间图片边界 （如果中间图片是显示的状态 设置中间图片右侧距离中间文字的间距）
    checkMargin(iv_center, false);
  }

  /**
   * 设置中间编辑框
   */
  public void setEditTextCenter(String str) {
    et_center.setVisibility(VISIBLE);
    et_center.setText(str);
  }
/**
   * 设置中间编辑框Hint内容
   */
  public void setEditTextCenterHint(String hintStr) {
    et_center.setVisibility(VISIBLE);
    et_center.setHint(hintStr);
  }

  /**
   * 设置右侧第一张图片
   */
  public void setImageViewRightFirst(int resourceId) {
    setImageView(iv_right_first, resourceId, 22, 22);
    //设置右侧文字边界 （如果右侧文字是显示的状态 设置右侧文字左侧距离右侧图片的间距）
    checkMargin(tv_right, true);
    //设置右侧末尾图片边界 （如果右侧图片末尾是显示的状态 设置右侧图片末尾距离右侧第一张图片间距或右侧文字的间距）
    checkMargin(iv_right_end, true);
  }

  /**
   * 设置右侧第一张图片
   */
  public void setImageViewRightFirst(Drawable drawable) {
    setImageView(iv_right_first, drawable, 22, 22);
    //设置右侧文字边界 （如果右侧文字是显示的状态 设置右侧文字左侧距离右侧图片的间距）
    checkMargin(tv_right, true);
    //设置右侧末尾图片边界 （如果右侧图片末尾是显示的状态 设置右侧图片末尾距离右侧第一张图片间距或右侧文字的间距）
    checkMargin(iv_right_end, true);
  }

  /**
   * 设置右侧文字
   */
  public void setTextViewRight(String str) {
    setTextView(tv_right, str);
    checkMargin(iv_right_first, false);
    checkMargin(iv_right_end, true);
  }

  /**
   * 设置右侧最后一张图片
   */
  public void setImageViewRightEnd(int resourceId) {
    setImageView(iv_right_end, resourceId, 22, 22);
    checkMargin(tv_right, false);
    checkMargin(iv_right_first, false);
  }

  /**
   * 设置右侧最后一张图片
   */
  public void setImageViewRightEnd(Drawable drawable) {
    setImageView(iv_right_end, drawable, 22, 22);
    checkMargin(tv_right, false);
    checkMargin(iv_right_first, false);
  }

  /**
   * 设置右侧按钮
   */
  public void setButtonRight(String str, OnClickListener onClickListener) {
    bt_right.setVisibility(VISIBLE);
    bt_right.setText(str);
    bt_right.setOnClickListener(onClickListener);
  }



  /**
   * 有时候只显示中间
   */
  public void setSingleCenter() {

    LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
    params.gravity=Gravity.CENTER;
    ll_center.setLayoutParams(params);
    ll_center.setGravity(Gravity.CENTER);
    LayoutParams params2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
    ll_right.setLayoutParams(params2);
    LayoutParams params3 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
    ll_left.setLayoutParams(params3);
    tv_left.setVisibility(GONE);
  }

  /**
   * 有时候需要扩大中间的布局大小 改变中间布局和右侧的权重
   */
  public void setBigCenter() {

    LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 5);
    ll_center.setLayoutParams(params);
    LayoutParams params2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2);
    ll_right.setLayoutParams(params2);
  }

  /**
   * 有时候需要扩大中间的布局大小 改变中间布局和右侧的权重
   */
  public void setBigCenterNoRight() {

    LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 6);
    ll_center.setLayoutParams(params);
    LayoutParams params2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
    ll_right.setLayoutParams(params2);
  }

  /**
   * 有时候需要扩大左侧的布局大小 改变权重
   */
  public void setBigLeft() {
    LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 8);
    ll_left.setLayoutParams(params);
    LayoutParams params1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
    ll_center.setLayoutParams(params1);
    LayoutParams params2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
    ll_right.setLayoutParams(params2);
  }


  /**
   * 有时候需要扩大右侧侧的布局大小 改变权重
   */
  public void setBigRight() {
    LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3);
    ll_left.setLayoutParams(params);
    LayoutParams params1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2);
    ll_center.setLayoutParams(params1);
    LayoutParams params2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 5);
    ll_right.setLayoutParams(params2);
  }

  /**
   * 高度自适应
   */
  public void heightWrapContent(){
    LayoutParams params= (LayoutParams) ll_content.getLayoutParams();
    params.height=ViewGroup.LayoutParams.WRAP_CONTENT;
    ll_content.setLayoutParams(params);
  }


  /**
   * 显示隐藏底部分割线
   */
  public void showLine(boolean show) {
    if (show) {
      v_line.setVisibility(VISIBLE);
    } else {
      v_line.setVisibility(GONE);
    }
  }

  /**
   * 添加点击监听
   */
  public void setOnContentClickListener(OnClickListener onLayoutClickListener) {
    this.ll_content.setOnClickListener(onLayoutClickListener);
  }
  /**
   * 添加点击监听
   */
  public void setOnContentClickListener(OnClickListener onLayoutClickListener,int tag) {
    this.ll_content.setTag(tag);
    this.ll_content.setOnClickListener(onLayoutClickListener);
  }
  /**
   * 添加点击监听
   */
  public void setOnContentClickListener(OnClickListener onLayoutClickListener,Object tag) {
    this.ll_content.setTag(tag);
    this.ll_content.setOnClickListener(onLayoutClickListener);
  }

  private void setImageView(ImageView iv, int resourceId, int width, int height) {
    iv.setVisibility(VISIBLE);
    iv.setImageResource(resourceId);
    iv.setLayoutParams(LayoutHelper.getLinearLayoutLayoutParaments(getContext(), width, height));
  }

  private void setImageView(ImageView iv, Drawable drawable, int width, int height) {
    iv.setVisibility(VISIBLE);
    iv.setImageDrawable(drawable);
    iv.setLayoutParams(LayoutHelper.getLinearLayoutLayoutParaments(getContext(), width, height));
  }

  private void setTextView(TextView tv, String str) {
    setTextView(tv,str,false);
  }
  private void setTextView(TextView tv, String str,boolean typefaceBold) {
    tv.setText(str);
    if(typefaceBold){//设置字体加粗
      tv .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }
    tv.setVisibility(VISIBLE);
  }

  //不设置高度 原先高度 45
  public void noHeight() {
    LinearLayout.LayoutParams params = (LayoutParams) ll_base.getLayoutParams();
    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
    ll_base.setLayoutParams(params);
    showLine(false);
  }


  //不设置高度 原先高度 45
  public void setHeight(int height) {

    LinearLayout.LayoutParams params = (LayoutParams) ll_base.getLayoutParams();
    params.height = height;
    ll_base.setLayoutParams(params);

    LinearLayout.LayoutParams paramsContent = (LayoutParams) ll_content.getLayoutParams();
    paramsContent.height = ViewGroup.LayoutParams.WRAP_CONTENT;
    ll_content.setLayoutParams(paramsContent);

    showLine(false);
  }


  //设置margin (检测元素是否显示 么有显示无需设置 有显示 先判断是否已经设置 没有设置 则设置)  left==true 检测传入元素的左边界 反之右边界
  private void checkMargin(View v, boolean left) {
    int marginSize = 5;
    if (v.getVisibility() == View.VISIBLE) {
      ViewGroup.LayoutParams params = v.getLayoutParams();
      if (params instanceof LinearLayout.LayoutParams) {
        LinearLayout.LayoutParams paramsll = (LinearLayout.LayoutParams) params;
        if (left) {
          if (paramsll.leftMargin <= 0) {
            setMarginLeft(v, marginSize);
          }
        } else {
          if (paramsll.rightMargin <= 0) {
            setMarginRight(v, marginSize);
          }
        }
      } else {
        setMarginLeft(v, marginSize);
      }
    }
  }

  //设置左边界
  private void setMarginLeft(View v, int marginSize) {
    LinearLayout.LayoutParams params = LayoutHelper.getLinearLayoutLayoutParamentsWrapContent();
    params.setMargins(PhoneHelper.dip2px(getContext(), marginSize), 0, 0, 0);
    v.setLayoutParams(params);
  }

  //设置右边界
  private void setMarginRight(View v, int marginSize) {
    LinearLayout.LayoutParams params = LayoutHelper.getLinearLayoutLayoutParamentsWrapContent();
    params.setMargins(0, 0, PhoneHelper.dip2px(getContext(), marginSize), 0);
    v.setLayoutParams(params);
  }
}
