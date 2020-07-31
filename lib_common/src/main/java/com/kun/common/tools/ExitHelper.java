package com.kun.common.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/8/2.
 * 双击退出程序
 */
public class ExitHelper {
      /**
       * log
       */
    private String log="双击退出程序";

    private Activity activity;

    private ExitHelperListener exitHelperListener;
    /**
     * 点击次数
     */
    private int clickCount=0;
    /**
     * 第一次点击 执行后又可以重置为第一次点击
     */
    private boolean firstClick=true;


  @SuppressLint("HandlerLeak")
    private Handler Handler_exit = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
              case 1:
                  firstClick=true;
                if(clickCount>=5){
                    doExitFiveClick();
                }else if(clickCount>=2&&clickCount<5){
                    doExit();
                }
                break;
              default:break;
            }

        }
    };

    public ExitHelper(Activity activity) {
        this.activity = activity;
    }

    public void exit(int repeatCount) {
        if (repeatCount == 0) {
            if(firstClick) {
                clickCount=0;
                firstClick=false;
                Handler_exit.sendEmptyMessageDelayed(1, 1000);
            }
             clickCount++;
             Toast.makeText(activity, "再按一次退出程序", Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * 双击退出
     */
    private void doExit(){
        LogHelper.i(log,"双击退出执行");
        if (exitHelperListener != null) {
            exitHelperListener.doExit();
        }
        activity.finish();
        System.exit(0);
    }

    /**
     * 点击5次
     */
    private void doExitFiveClick(){
        LogHelper.i(log,"点击五下执行");
        if (exitHelperListener != null) {
            exitHelperListener.doExitFiveClick();
        }
    }

    /**
     * 添加关闭程序监听
     */
    public void setExitHelperListener(ExitHelperListener exitHelperListener) {
        this.exitHelperListener = exitHelperListener;
    }

    /**
     * 关闭程序监听
     */
    public interface ExitHelperListener {
        void doExit();
        void doExitFiveClick();
    }
}
