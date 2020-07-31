package com.kun.common.tools;

/**
 * Created by Administrator on 2019/5/28.
 */

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

/**
 * 单次点击和多次点击类
 * 使用
 * 1、创建 添加监听
 *    clickHelper=new ClickHelper();
      clickHelper.setClickHelperInterface(this);
   2、事件每次点击时都调用 click方法 click方法会统计点击次数等操作
      clickHelper.click();
   3、最后监听返回单次点击还是多次点击 默认单次点击时间800毫秒 多次点击为5次2000毫秒

   原理:使用handler的延迟发送 统计单次点击时间内的点击数量 和多次点击时间内的数量来判断点击类型
 */
public class ClickHelper {

  private static final int WHAT_ONECLICK = 1, WHAT_MAXCLICK = 2;//单次点击的what 和多次点击的what  handler使用

  public int currentCount = 0, oneClickCount = 1, maxClickCount = 5;//当前点击数量，单次点数量，多次点击数量

  private long oneClickTime = 800, maxClickTime = 2000;//单次点击时间，多次点击时间

  private boolean running = false;//是否在运行

  @SuppressLint("HandlerLeak")
  public Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case WHAT_ONECLICK://单次点击的时间到期
          if (currentCount == oneClickCount) {
            if (clickHelperInterface != null) {
              clickHelperInterface.oneClick();
            }
            reset();
          }
          break;
        case WHAT_MAXCLICK://多次点击的时间到期
          if (currentCount == maxClickCount) {
            if (clickHelperInterface != null) {
              clickHelperInterface.maxClick();
            }
          }
          reset();
          break;
      }
    }
  };


  private void reset() {
    running = false;
    currentCount = 0;
  }

  /**
   * 点击
   */
  public void click() {
    if (!running) {
      start();
    }
    currentCount++;
  }

  private void start() {
    running = true;
    handler.sendEmptyMessageDelayed(WHAT_ONECLICK, oneClickTime);
    handler.sendEmptyMessageDelayed(WHAT_MAXCLICK, maxClickTime);
  }


  private ClickHelperInterface clickHelperInterface;

  public void setClickHelperInterface(ClickHelperInterface clickHelperInterface) {
    this.clickHelperInterface = clickHelperInterface;
  }

  public interface ClickHelperInterface {
    void oneClick();

    void maxClick();
  }
}
