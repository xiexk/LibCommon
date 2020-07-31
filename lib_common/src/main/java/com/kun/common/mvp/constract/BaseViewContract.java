package com.kun.common.mvp.constract;

import android.view.View;

/**
 * Created by Administrator on 2018/9/18.
 * 基础View 合同
 */

public interface BaseViewContract  extends BaseContract{
  /**
   * 设置背景透明
   */
  void transparent();

  void showToastShot(String message);

  void showToastLong(String message);

  void showToastLongDebug(String message);

  void showError(int type, String message);

  void clickError();

  void showCustomError(int layoutId);//展示自定义的错误界面

  void showHintDialog(String message);

  void showHintDialog(String message, View.OnClickListener onClickListener);

  void showHintDialog(String title, String message, View.OnClickListener onClickListener);

  void showHintDialog(String title, String message, View.OnClickListener onClickListener,long timeAutoDismiss);

  void hideHintDialog();

  void showWaitingDialog();

  void hindWaitingDialog();

  void toActivity(Class activityClass);

  void msg(String message,int code);

  /**
   * 处理屏幕旋转  手动控制模式下可以控制屏幕横竖屏 非手动控制由系统控制
   */
  void dealScreenOritation();
}
