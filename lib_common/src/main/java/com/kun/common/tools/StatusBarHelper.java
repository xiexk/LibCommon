package com.kun.common.tools;

import com.githang.statusbar.StatusBarCompat;

import android.app.Activity;
import android.content.res.Resources;

/**
 * Created by Administrator on 2018/9/27.
 * 状态栏工具类
 *
 * 使用1、setStatusBarColor（this,R.color.red） 设置状态栏颜色
 2、setTranslucent（this）设置沉浸式状态栏
 3 setFitsSystemWindows设置内容距离顶部距离 （单独的view页可以设置setFitsSystemWindows）
 */

public class StatusBarHelper {

  /**
   *  设置状态栏颜色 和透明度
   */
  public static void setStatusBarColor(Activity activity, int statusColor) {
    StatusBarCompat.setStatusBarColor(activity, activity.getResources().getColor(statusColor));
  }

  /**
   * 设置沉浸式状态栏 （activity全屏 状态栏透明）
   * @param activity activity
   */
  public static void setTranslucent(Activity activity) {
    StatusBarCompat.setTranslucent(activity.getWindow(), true);
  }

  /**
   * 设置activity pading顶部距离 状态栏高度
   * @param activity activity
   * @param fitSystemWindows 是否padding
   */
  public static void setFitsSystemWindows(Activity activity, boolean fitSystemWindows) {
    StatusBarCompat.setFitsSystemWindows(activity.getWindow(), fitSystemWindows);
  }

  /**
   * 获取状态栏的高度
   * @param activity activity
   * @return 状态了高度
   */
  private static int getStatusBarHeight(Activity activity) {
    // 插件式换肤：怎么获取资源的，先获取资源id，根据id获取资源
    Resources resources = activity.getResources();
    int statusBarHeightId = resources.getIdentifier("status_bar_height", "dimen", "android");
    return resources.getDimensionPixelOffset(statusBarHeightId);
  }
}


