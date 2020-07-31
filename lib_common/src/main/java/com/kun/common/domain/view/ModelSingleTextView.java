package com.kun.common.domain.view;

import com.kun.common.domain.ArgumentData;

/**
 * Created by Administrator on 2018/9/12.
 * 单行textView的model
 */

public class ModelSingleTextView {
  public int leftIvId;

  public String leftTv;

  public int centerIvId;

  public String centerTv;

  public int rightIvId;

  public String rightTv;

  public boolean rightIvVisible = true;//默认显示右侧右箭头图标

  public boolean margin;

  public String clickUrl;

  public ArgumentData argumentData;
}
