package com.kun.common.tools;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;


/**
 * @author kun
 * Date: 2020/7/9
 * Time: 11:19
 */

public class HelperBackActivity extends CountDownTimer {

    private Context context;
    private Class toActivity;

    /**
     * 参数 millisInFuture       倒计时总时间（如60S，120s等）
     * 参数 countDownInterval    渐变时间（每次倒计1s）
     */
    public HelperBackActivity(long millisInFuture, long countDownInterval, Context context,Class toActivity) {
        super(millisInFuture, countDownInterval);
        this.context = context;
        this.toActivity=toActivity;
    }

    // 计时完毕时触发
    @Override
    public void onFinish() {
//跳转到登陆界面，并将栈底的Activity全部都销毁
        Intent intent = new Intent(context, toActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    // 计时过程显示
    @Override
    public void onTick(long millisUntilFinished) {

    }


}
