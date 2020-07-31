package com.kun.common.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kun.common.R;
import com.kun.common.mvp.BaseActivityFragmentController;
import com.kun.common.mvp.constract.BaseViewContract;
import com.kun.common.tools.HelperBackActivity;
import com.kun.common.tools.StatusBarHelper;


/**
 * Created by Administrator on 2018/8/30.
 * BaseActivity 头部、内容、底下Activity
 */

public class BaseHCFActivity extends AppCompatActivity implements View.OnClickListener, BaseViewContract {

    public BaseActivityFragmentController baseActivityFragmentController;
    /**
     * 全屏
     */
    private boolean fullScreen;
    /**
     * 打开页面停留倒计时
     */
    private boolean enableAutoBack;
    /**
     * 自动返回倒计时
     */
    private long autoBackTime = 60_000;
    /**
     * 自动返回的界面
     */
    private Class autoBackActivity;
    /**
     * 自动退出工具
     */
    private HelperBackActivity helperBackActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivityFragmentController = new BaseActivityFragmentController(this, this);
        dealScreenOritation();
        setContentView(baseActivityFragmentController.getBaseView());
        //设置状态栏颜色
        setStatusBarColor(R.color.white);
        initView();
        initData();
        initListener();
    }

    /**
     * 打开页面自动返回方法
     */
    public void enableAutoBack(Class autoBackActivity){
        this.enableAutoBack=true;
        this.autoBackActivity=autoBackActivity;
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    public void setStatusBarColor(int color) {
        StatusBarHelper.setStatusBarColor(this, color);
    }


    public void initView() {
    }

    public void initData() {
        if (enableAutoBack) {
            helperBackActivity = new HelperBackActivity(autoBackTime, 1_000, this, autoBackActivity);
        }
    }

    public void initListener() {
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return baseActivityFragmentController.getBaseView().findViewById(id);
    }

    @Override
    public void transparent() {
        baseActivityFragmentController.transparent();
    }

    @Override
    public void showToastShot(String message) {
        baseActivityFragmentController.showToastShot(message);
    }

    @Override
    public void showToastLong(String message) {
        baseActivityFragmentController.showToastLong(message);
    }

    @Override
    public void showToastLongDebug(String message) {
        baseActivityFragmentController.showToastLongDebug(message);
    }

    @Override
    public void showError(int imageId, String message) {
        baseActivityFragmentController.showError(imageId, message);
    }

    @Override
    public void clickError() {

    }

    @Override
    public void showCustomError(int layoutId) {
        baseActivityFragmentController.showCustomError(layoutId);
    }

    @Override
    public void showHintDialog(String message) {
        baseActivityFragmentController.showHintDialog(message);
    }

    @Override
    public void showHintDialog(String message, View.OnClickListener onClickListener) {
        baseActivityFragmentController.showHintDialog(message, onClickListener);
    }

    @Override
    public void showHintDialog(String title, String message, View.OnClickListener onClickListener) {
        baseActivityFragmentController.showHintDialog(title, message, onClickListener);
    }

    @Override
    public void showHintDialog(String title, String message, View.OnClickListener onClickListener, long timeAutoDismiss) {
        baseActivityFragmentController.showHintDialog(title, message, onClickListener, timeAutoDismiss);
    }

    @Override
    public void hideHintDialog() {
        baseActivityFragmentController.hideHintDialog();
    }

    @Override
    public void showWaitingDialog() {
        baseActivityFragmentController.showWaitingDialog();
    }

    @Override
    public void hindWaitingDialog() {
        baseActivityFragmentController.hindWaitingDialog();
    }

    @Override
    public void toActivity(Class activityClass) {
        baseActivityFragmentController.toActivity(activityClass);
    }

    @Override
    public void msg(String message, int code) {

    }

    @Override
    public void dealScreenOritation() {
        baseActivityFragmentController.dealScreenOritation();
    }

    /**
     * 全屏
     */
    public void fullscreen() {
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        // 定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        // 获得当前窗体对象
        Window window = getWindow();
        // 设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        fullScreen = true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (fullScreen) {
            hideNavigation();
        }
    }

    /**
     * 全屏，自动隐藏导航栏和虚拟按键
     */
    public void hideNavigation() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    @Override
    protected void onResume() {
        if (enableAutoBack) {
            startAutoBack();
        }
        super.onResume();

    }

    /**
     * 启动自动返回
     */
    private void startAutoBack() {

        new Handler(getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                if (helperBackActivity != null) {
                    helperBackActivity.start();
                }
            }
        });
    }

    /**
     * 主要的方法，重写dispatchTouchEvent
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            //获取触摸动作，如果ACTION_UP，计时开始。
            case MotionEvent.ACTION_UP:
                if (enableAutoBack && helperBackActivity != null) {
                    helperBackActivity.start();
                }
                break;
            //否则其他动作计时取消
            default:
                if (enableAutoBack && helperBackActivity != null) {
                    helperBackActivity.cancel();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (enableAutoBack && helperBackActivity != null) {
            helperBackActivity.cancel();
        }
    }
}
