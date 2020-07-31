package com.kun.common.tools;

import android.view.View;

/**
 * Created by Administrator on 2019/4/11.
 */

public class ViewHelper {

  public static int getMeasureHeight(View view){
    int width = View.MeasureSpec.makeMeasureSpec(0,
        View.MeasureSpec.UNSPECIFIED);
    int height = View.MeasureSpec.makeMeasureSpec(0,
        View.MeasureSpec.UNSPECIFIED);
    view.measure(width, height);
    return view.getMeasuredHeight();
  }
}
