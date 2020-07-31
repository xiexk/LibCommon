package com.kun.app.popwin;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2018/12/10.
 * BasePopWindow
 */

public class BasePopWindow {
  public PopupWindow popupWindow;

  public Activity context;

  public View contentView;

  public int width;

  public int height;

  public int x = 0;

  public int y = 0;

  private int anim = -1;

  private boolean marchWidth;

  /**
   * 从下到上动画
   */
  public static int ANIM_DOWN = R.style.PopWindowAnim_Down_To_Up;

  /**
   * 从上到下动画
   */
  public static int ANIM_TOP = R.style.PopWindowAnim_Up_To_Down;

  /**
   * 从左到右动画
   */
  public static int ANIM_LEFT = R.style.PopWindowAnim_Left_To_Right;

  /**
   * 从右到左动画
   */
  public static int ANIM_RIGHT = R.style.PopWindowAnim_Right_To_Left;

  /**
   * 渐入渐出动画
   */
  public static int ANIM_Alpha = R.style.PopWindowAnim_Alpha;

  public int location = -1;

  private int gravity;

  /**
   * 位置左上
   */
  public final static int LOCATION_LEFT_TOP = 0x1;

  /**
   * 位置上中
   */
  public final static int LOCATION_TOP_CENTER = 0x2;

  /**
   * 位置右上
   */
  public final static int LOCATION_RIGHT_TOP = 0x3;

  /**
   * 位置左中
   */
  public final static int LOCATION_LEFT_CENTER = 0x4;

  /**
   * 位置右中
   */
  public final static int LOCATION_RIGHT_CENTER = 0x5;

  /**
   * 位置左下
   */
  public final static int LOCATION_LEFT_BOTTOM = 0x6;

  /**
   * 位置下中
   */
  public final static int LOCATION_BOTTOM_CENTER = 0x7;

  /**
   * 位置下右
   */
  public final static int LOCATION_RIGHT_BOTTOM = 0x8;

  /**
   * 位置屏幕中间
   */
  public final static int LOCATION_CENTER = 0x9;

  public BasePopWindow(Activity context, int viewId) {
    this.context = context;
    contentView = LayoutInflater.from(context).inflate(viewId, null);
    initHeightWidth();
    initXY();
    initPopWindow();
  }


  public BasePopWindow(Activity context, View viewContent) {
    this.context = context;
    contentView = viewContent;
    initHeightWidth();
    initXY();
    initPopWindow();
  }

  public BasePopWindow(Activity context, View viewContent, boolean marchWidth) {
    this.context = context;
    this.marchWidth = marchWidth;
    this.contentView = viewContent;
    initHeightWidth();
    initXY();
    initPopWindow();
  }

  private void initPopWindow() {
    //   popupWindow = new PopupWindow(contentView,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
    //popwindow一定要指定具体大小不然显示会错乱
    if (marchWidth) {
      popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.WRAP_CONTENT, true);
    } else {
      popupWindow = new PopupWindow(contentView, width, height, true);
    }

    popupWindow.setTouchable(true);
    popupWindow.setBackgroundDrawable(new ColorDrawable());
    setGrayBackGround();
  }

  //初始化大小
  private void initHeightWidth() {
    contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
    // 计算contentView的高宽
    width = contentView.getMeasuredHeight();
    height = contentView.getMeasuredWidth();
  }



  //设置xy坐标
  private void initXY() {
    x = 0;
    y = 0;
  }

  //设置动画styleId
  public void setAnim(int animId) {
    anim = animId;
    if (anim != -1) {
      popupWindow.setAnimationStyle(anim);
    }
  }

  //设置popwindow展示方位
  public void setLocation(int location) {
    this.location = location;
    switch (location) {
      case LOCATION_LEFT_TOP:
        gravity = Gravity.START | Gravity.TOP;
        break;
      case LOCATION_TOP_CENTER:
        gravity = Gravity.TOP;
        break;
      case LOCATION_RIGHT_TOP:
        gravity = Gravity.END | Gravity.TOP;
        break;
      case LOCATION_LEFT_CENTER:
        gravity = Gravity.START;
        break;
      case LOCATION_RIGHT_CENTER:
        gravity = Gravity.END;
        break;
      case LOCATION_LEFT_BOTTOM:
        gravity = Gravity.START | Gravity.BOTTOM;
        break;
      case LOCATION_BOTTOM_CENTER:
        gravity = Gravity.BOTTOM;
        break;
      case LOCATION_RIGHT_BOTTOM:
        gravity = Gravity.END | Gravity.BOTTOM;
        break;
      case LOCATION_CENTER:
        gravity = Gravity.CENTER;
        break;
    }
  }

  //显示
  public void show() {
    View parent = context.findViewById(android.R.id.content);
    popupWindow.showAtLocation(contentView, gravity, x, y);
  }


  //设置灰色背景
  private void setGrayBackGround() {
    //设置灰色背景
    WindowManager.LayoutParams lp = context.getWindow().getAttributes();
    lp.alpha = 0.3f;
    context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    context.getWindow().setAttributes(lp);
    //解除灰色背景
    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
      @Override
      public void onDismiss() {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 1.0f;
        context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
      }
    });
  }

  //消失
  public void dismiss() {
    popupWindow.dismiss();
  }

  //获取屏幕高度
  private static int getScreenHeight(Activity c) {
    Display display = c.getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    return size.y;// 获得高度，获得宽度也类似
  }

  //获取屏幕宽度
  public static int getScreenWidth(Activity c) {
    Display display = c.getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    return size.x;// 获得宽度，获得高度也类似
  }
}
