package com.kun.common.widge.edittext;


import com.kun.common.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


/**
 * Created by Administrator on 2018/9/29.
 * 单行EditText
 * 使用
 *  *  1、填写hint内容 eg: edittextsingle:hintText="我是hint内容"
 *  *  2、填写左侧内容 eg: edittextsingle:leftText="我是左侧内容"
 *  *  3、显示分割线 eg:  edittextsingle:showLine="true"
 */

public class EditTextSingle extends LinearLayout {

  public TextView tv_name;

  public View line;

  public EditText et_content;

  private String hintText, leftText;

  private boolean showLine;


  public EditTextSingle(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  private void init(Context context, @Nullable AttributeSet attrs) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.edittextsingle);
    hintText = typedArray.getString(R.styleable.edittextsingle_hintText);
    leftText = typedArray.getString(R.styleable.edittextsingle_leftText);
    showLine = typedArray.getBoolean(R.styleable.edittextsingle_showLine, false);
    LayoutInflater.from(context).inflate(R.layout.mainlib_layout_single_edittext, this);

    this.tv_name = findViewById(R.id.mainlib_layout_single_edittext_tv_name);
    this.et_content = findViewById(R.id.mainlib_layout_single_edittext_et);
    this.line = findViewById(R.id.mainlib_layout_single_line);

    if (leftText != null) {
      tv_name.setText(leftText);
    }
    if (hintText != null) {
      et_content.setHint(hintText);
    }
    if (showLine) {
      showLine(showLine);
    }
  }

  public void showLine(boolean showLine) {
    this.showLine = showLine;
    if (showLine) {
      line.setVisibility(VISIBLE);
    } else {
      line.setVisibility(GONE);
    }
  }
}
