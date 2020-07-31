package com.kun.common.support.bottombar;

import java.lang.reflect.Field;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;

import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2019/2/12.
 * BottomNavigation 处理类
 */

public class BottomNavigationFixHelper {

  public static void setItem(BottomNavigationBar bottomNavigationBar, int textSize) {
    Class barClass = bottomNavigationBar.getClass();
    Field[] fields = barClass.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];
      field.setAccessible(true);
      if (field.getName().equals("mTabContainer")) {
        try {
          //反射得到 mTabContainer
          LinearLayout mTabContainer = (LinearLayout) field.get(bottomNavigationBar);
          for (int j = 0; j < mTabContainer.getChildCount(); j++) {
            //获取到容器内的各个Tab
            View view = mTabContainer.getChildAt(j);

            //获取到Tab内的文字控件
            TextView labelView = view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_title);
            //计算文字的高度DP值并设置，setTextSize为设置文字正方形的对角线长度，所以：文字高度（总内容高度减去间距和图片高度）*根号2即为对角线长度，此处用DP值，设置该值即可。
            labelView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
            labelView.setIncludeFontPadding(false);
          }
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
