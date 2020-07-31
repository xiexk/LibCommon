package com.kun.common.widge.dialog;


import com.kun.common.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author kun 提示框
 * 使用方法
 * 有点击监听（有监听时显示取消按钮）
 * AppUpdateLibHintDialog hintDialog = new AppUpdateLibHintDialog(context, message,onClickListener);
 * hintDialog.show();
 * 如果有设置定时关闭同时确定监听是外部传入的 一定要调用 cancerTimer（）取消定时器后关闭
 */
public class HintDialog extends Dialog implements View.OnClickListener {

    private TextView title, tv, ok;
    // 文本内容
    private String str_title, str_content;

    private View.OnClickListener onClickListener;
    /**
     * 自动关闭弹窗定时器
     */
    private Timer timer;
    /**
     * 自动关闭弹窗定时器内容
     */
    private TimerTaskHideDialog timerTaskHideDialog;
    /**
     * 自动关闭弹弹窗时间
     */
    private long timeAutoDisMiss = -1;


    /**
     * @param context    上下文
     * @param tv_content 文本内容 String类型 重载构造函数 可以为null(显示默认文案)
     */
    public HintDialog(Context context, String tv_content) {
        super(context, R.style.style_dialog_hint);
        init(null, tv_content, null, -1);
    }

    public HintDialog(Context context, String tv_content, View.OnClickListener onClickListener) {
        super(context, R.style.style_dialog_hint);
        init(null, tv_content, onClickListener, -1);
    }


    public HintDialog(Context context, String tv_title, String tv_content, View.OnClickListener onClickListener) {
        super(context, R.style.style_dialog_hint);
        init(tv_title, tv_content, onClickListener, -1);
    }

    public HintDialog(Context context, String tv_title, String tv_content, View.OnClickListener onClickListener, long timeAutoDismiss) {
        super(context, R.style.style_dialog_hint);
        init(tv_title, tv_content, onClickListener, timeAutoDismiss);
    }

    private void init(String tv_title, String tv_content, View.OnClickListener onClickListener, long timeAutoDismiss) {
        this.str_title = tv_title;
        this.str_content = tv_content;
        this.onClickListener = onClickListener;
        this.timeAutoDisMiss = timeAutoDismiss;
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlib_layout_dialog_hint);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        title = findViewById(R.id.mainlib_dialog_title);
        ok = findViewById(R.id.mainlib_dialog_hint_ok);
        tv = findViewById(R.id.mainlib_dialog_tv);
        if (onClickListener != null) {
            //显示竖线
            findViewById(R.id.mainlib_login_return_dialog_centerview).setVisibility(View.VISIBLE);
            //显示取消按钮
            findViewById(R.id.mainlib_dialog_hint_cancer).setVisibility(View.VISIBLE);
        }
    }

    private void initListener() {

        if (onClickListener != null) {
            ok.setOnClickListener(onClickListener);
            findViewById(R.id.mainlib_dialog_hint_cancer).setOnClickListener(this);
        } else {
            ok.setOnClickListener(this);
        }
    }

    private void initData() {
        if (str_title != null) {
            title.setText(str_title);
        }
        if (str_content != null) {
            tv.setText(str_content);
        }
        if (timeAutoDisMiss != -1) {
            timer = new Timer();
            timerTaskHideDialog = new TimerTaskHideDialog();
            timer.schedule(timerTaskHideDialog, timeAutoDisMiss);
        }
    }

    public void setContent(String content) {
        str_content = content;
        tv.setText(str_content);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        cancerTimer();
        if (i == R.id.mainlib_dialog_hint_ok) {
            this.dismiss();
        } else if (i == R.id.mainlib_dialog_hint_cancer) {
            this.dismiss();
        }
    }

    /**
     * 取消定时器
     */
    public void cancerTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTaskHideDialog != null) {
            timerTaskHideDialog.cancel();
            timerTaskHideDialog = null;

        }
    }

    /**
     * 定时关闭
     */
    public class TimerTaskHideDialog extends TimerTask {
        @Override
        public void run() {
            dismiss();
        }
    }
}
