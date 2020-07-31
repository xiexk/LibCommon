package com.kun.common.tools;

import android.app.Activity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/10/18.
 * TextView处理工具
 * textview部分字体颜色变化
 */

public class TextViewHelper {
  /**
   * 改变textView中匹配到的文字的颜色
   * 调用示例
   * （1）  TextViewHelper.changeTextViewColor(context,holder.tv_price,"我",context.getResources().getColor(R.color.yellow)); //正常调用
   * （2）  TextViewHelper.changeTextViewColor(context,holder.tv_price,"\ue711",context.getResources().getColor(R.color.yellow));//使用TextxViewIconFont时改变图标颜色调用
   */
  public static void changeTextViewColor(Activity context, TextView tv, String str, int color) {
    try {
      String tvStr = tv.getText().toString();
      int start = tvStr.indexOf(str);
      int end = start + str.length();
      SpannableStringBuilder builder = new SpannableStringBuilder(tvStr);
      ForegroundColorSpan span = new ForegroundColorSpan(color);
      builder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      tv.setText(builder);
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}
