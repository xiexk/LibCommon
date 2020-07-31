package com.kun.common.widge.layout;

import com.kun.common.R;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/9/3.
 * checkbox 勾选组件 主要适用于 勾选协议
 *
 * 使用：
 *

 */

public class LayoutClickCheckBox extends LinearLayout {

  private TextView tv_right;

  private CheckBox cb;

  private String strRight;


  public LayoutClickCheckBox(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initView(context, attrs);
  }

  private void initView(Context context, AttributeSet attrs) {

    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.layoutClickCheckBox);

    strRight = typedArray.getString(R.styleable.layoutClickCheckBox_tvright);


     LayoutInflater.from(context).inflate(R.layout.mainlib_layout_clickcheckbox, this);

    tv_right = findViewById(R.id.mainlib_layout_clickcheckbox_tv);
    cb = findViewById(R.id.mainlib_layout_clickcheckbox_checkbox);


    if (strRight != null) {
      setTextViewRight(strRight);
    }
  }


  /**
   * 设置右侧文字
   */
  public void setTextViewRight(String str) {
    tv_right.setText(str+"");
  }

  /**
   * 返回是否勾选
   * @return
   */
  public boolean getChecked(){
    return cb.isChecked();
  }

  public void setTextViewOnClickListener(OnClickListener onClickListener){
    this.tv_right.setOnClickListener(onClickListener);
  }


}
