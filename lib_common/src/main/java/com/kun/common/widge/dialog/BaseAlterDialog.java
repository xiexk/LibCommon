package com.kun.common.widge.dialog;

import com.kun.common.R;
import com.kun.common.tools.PhoneHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2018/10/10.
 * BaseAlterDialog
 */

public class BaseAlterDialog {

  private AlertDialog myDialog;

  private PopupWindow popupWindow;

  private Activity activity;

  public void showDialog(Activity activity, View anchorView, View contentView) {
    myDialog = new AlertDialog.Builder(activity, R.style.ActionSheetDialogStyle).create();
    myDialog.show();
    Window pop_window = myDialog.getWindow();
    pop_window.setContentView(contentView);
    this.activity = activity;
    Display display = activity.getWindowManager().getDefaultDisplay();
    @SuppressWarnings("deprecation")
    int width = display.getWidth();
    pop_window.setGravity(Gravity.LEFT | Gravity.TOP); // 此处可以设置dialog显示的位置
    int windowPos[] = calculatePopWindowPos(anchorView, contentView);
    WindowManager.LayoutParams p = pop_window.getAttributes();
    p.width = width;
    p.x = windowPos[0]; // 新位置X坐标
    p.y = windowPos[1]; // 新位置Y坐标

    pop_window.setAttributes(p);
  }

  public void showPopWindow(Activity activity, View anchorView, View contentView) {
    this.activity = activity;
    popupWindow = new PopupWindow(contentView,
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

    popupWindow.setTouchable(true);
    popupWindow.setBackgroundDrawable(new ColorDrawable());
    int windowPos[] = calculatePopWindowPos(anchorView, contentView);
    popupWindow.showAtLocation(contentView, Gravity.NO_GRAVITY, windowPos[0], windowPos[1]);
  }


  /**
   * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
   * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
   *
   * @param anchorView  呼出window的view
   * @param contentView window的内容布局
   * @return window显示的左上角的xOff, yOff坐标
   */
  public int[] calculatePopWindowPos(final View anchorView, final View contentView) {
    final int windowPos[] = new int[2];
    final int anchorLoc[] = new int[2];
    // 获取锚点View在屏幕上的左上角坐标位置
    anchorView.getLocationOnScreen(anchorLoc);
    final int anchorHeight = anchorView.getHeight();
    // 获取屏幕的高宽
    final int screenHeight = getScreenHeight(anchorView.getContext());
    final int screenWidth = getScreenWidth(anchorView.getContext());
    contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
    // 计算contentView的高宽
    final int windowHeight = contentView.getMeasuredHeight();
    final int windowWidth = contentView.getMeasuredWidth();
    // 判断需要向上弹出还是向下弹出显示
    final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
    if (isNeedShowUp) {

      windowPos[0] = screenWidth - windowWidth;
      windowPos[1] = anchorLoc[1] - windowHeight + PhoneHelper.getStatHeight(activity);
    } else {
      windowPos[0] = screenWidth - windowWidth;
      windowPos[1] = anchorLoc[1] + anchorHeight;
    }
    return windowPos;
  }

  /**
   * 获取屏幕高度(px)
   */
  public static int getScreenHeight(Context context) {
    return context.getResources().getDisplayMetrics().heightPixels;
  }

  /**
   * 获取屏幕宽度(px)
   */
  public static int getScreenWidth(Context context) {
    return context.getResources().getDisplayMetrics().widthPixels;
  }

  public void hideDailog() {
    if (myDialog != null) {
      myDialog.dismiss();
    }
    if (popupWindow != null) {
      popupWindow.dismiss();
    }
  }
}
