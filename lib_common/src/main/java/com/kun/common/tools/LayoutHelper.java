package com.kun.common.tools;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2018/9/5.
 */

public class LayoutHelper {

  public static LinearLayout.LayoutParams getLinearLayoutLayoutParaments(Context context, int width, int height) {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(PhoneHelper.dip2px(context, width),
        PhoneHelper.dip2px(context, height));
    return layoutParams;
  }

  public static LinearLayout.LayoutParams getLinearLayoutLayoutParamentsWrapContent() {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    return layoutParams;
  }

  public static LinearLayout.LayoutParams getLinearLayoutLayoutParamentsMarthParent(Context context) {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT);
    return layoutParams;
  }

  public static LinearLayout.LayoutParams getLinearLayoutLayoutParamentsMarthWidth() {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    return layoutParams;
  }

  public static RelativeLayout.LayoutParams getRelativeLayoutParaments(Context context, int width, int height) {
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(PhoneHelper.dip2px(context, width),
        PhoneHelper.dip2px(context, height));
    return layoutParams;
  }

  public static ViewGroup.LayoutParams getViewGroupLayoutParaments(Context context, int width, int height) {
    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(PhoneHelper.dip2px(context, width),
        PhoneHelper.dip2px(context, height));
    return layoutParams;
  }

  public static ViewGroup.LayoutParams getViewGroupLayoutParamentsMatchWidth() {
    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    return layoutParams;
  }

  public static ViewGroup.LayoutParams getViewGroupLayoutParamentsMatchParent() {
    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT);
    return layoutParams;
  }
}
