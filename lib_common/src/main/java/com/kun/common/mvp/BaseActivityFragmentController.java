package com.kun.common.mvp;

import com.kun.common.BaseApplication;
import com.kun.common.R;
import com.kun.common.mvp.constract.BaseViewContract;
import com.kun.common.tools.HelperToast;
import com.kun.common.tools.PhoneHelper;
import com.kun.common.widge.dialog.HintDialog;
import com.kun.common.widge.dialog.MyWaittingDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/9/5.
 * 对Activity和Fragment相同的东西进行封装控制
 */

public class BaseActivityFragmentController implements BaseViewContract, View.OnClickListener {

    private Activity context;

    private BaseViewContract baseViewContract;

    public RelativeLayout rl_base, rl_header, rl_footer, rl_content;

    private LinearLayout ll_error;

    private ImageView iv_error;

    private TextView tv_error;

    private MyWaittingDialog myWaittingDialog;

    public HintDialog hintDialog;

    public BaseActivityFragmentController(Activity context, BaseViewContract baseViewContract) {
        this.context = context;
        this.baseViewContract = baseViewContract;
        rl_base = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.mainlib_activity_base_hcf, null);
        rl_header = rl_base.findViewById(R.id.commonresourcelib_activity_base_hcf_head);
        rl_content = rl_base.findViewById(R.id.commonresourcelib_activity_base_hcf_content);
        rl_footer = rl_base.findViewById(R.id.commonresourcelib_activity_base_hcf_fotter);
        ll_error = rl_base.findViewById(R.id.mainlib_layout_error);
        iv_error = ll_error.findViewById(R.id.mainlib_layout_iv_tv_iv);
        tv_error = ll_error.findViewById(R.id.mainlib_layout_iv_tv_tv);
        // paddingTop(true);//处理状态栏挡住内容
        initListener();
    }

    private void initListener() {
        ll_error.setOnClickListener(this);
    }

    public View getBaseView() {
        return rl_base;
    }

    //设置头部
    public void setHeader(View headerView) {
        rl_header.removeAllViews();
        rl_header.addView(headerView);
    }

    //添加头部
    public void addHeader(View headerView) {
        rl_header.addView(headerView);
    }

    public void setHeader(int id) {
        ViewGroup.LayoutParams layoutParams
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View ll_header = LayoutInflater.from(context).inflate(id, null);
        ll_header.setLayoutParams(layoutParams);
        rl_header.addView(ll_header);
    }

    public void addFooter(View footerView) {
        rl_footer.addView(footerView);
    }

    public void addFooter(int id) {
        ViewGroup.LayoutParams layoutParams
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View ll_footer = LayoutInflater.from(context).inflate(id, null);
        ll_footer.setLayoutParams(layoutParams);
        rl_footer.addView(ll_footer);
    }

    public void addContent(int id) {
      LayoutInflater.from(context).inflate(id, rl_content);
    }

    public void paddingTop(boolean padding) {
        if (padding) {
            rl_header.setPadding(0, PhoneHelper.getStatHeight(context), 0, 0);
        } else {
            rl_header.setPadding(0, 0, 0, 0);
        }
    }


    @Override
    public void transparent() {
        rl_base.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        rl_content.setBackgroundColor(context.getResources().getColor(R.color.transparent));
    }

    @Override
    public void showToastShot(String message) {
       // Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        HelperToast.showToast(context, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void showToastLong(String message) {
       // Toast.makeText(context,message,Toast.LENGTH_LONG).show();
        HelperToast.showToast(context, message, Toast.LENGTH_LONG);
    }

    @Override
    public void showToastLongDebug(String message) {
        if(BaseApplication.debug){
            Toast.makeText(context,message,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showError(int imageId, String message) {
        ll_error.setVisibility(View.VISIBLE);
        try {
            iv_error.setImageDrawable(context.getResources().getDrawable(imageId));
            tv_error.setText(message + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        hindWaitingDialog();
    }

    @Override
    public void clickError() {
        ll_error.setVisibility(View.GONE);
        baseViewContract.clickError();
    }

    @Override
    public void showCustomError(int layoutId) {
        ViewGroup.LayoutParams layoutParams
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View ll_CustomErrorPage = LayoutInflater.from(context).inflate(layoutId, null);
        ll_CustomErrorPage.setLayoutParams(layoutParams);
        ll_error.setVisibility(View.VISIBLE);
        ll_error.removeAllViews();
        ll_error.addView(ll_CustomErrorPage);
        ll_error.setOnClickListener(null);
    }

    @Override
    public void showHintDialog(String message) {
        if (hintDialog != null) {
            if (hintDialog.isShowing()) {
                hintDialog.setContent(message);
            } else {
                hintDialog = new HintDialog(context, message);
                hintDialog.show();
            }
        } else {
            hintDialog = new HintDialog(context, message);
            hintDialog.show();
        }
    }

    @Override
    public void showHintDialog(String message, View.OnClickListener onClickListener) {
        hintDialog = new HintDialog(context, message, onClickListener);
        hintDialog.show();
    }

    @Override
    public void showHintDialog(String title, String message, View.OnClickListener onClickListener) {
        hintDialog = new HintDialog(context, title, message, onClickListener);
        hintDialog.show();
    }

    @Override
    public void showHintDialog(String title, String message, View.OnClickListener onClickListener, long timeAutoDismiss) {
        hintDialog = new HintDialog(context, title, message, onClickListener,timeAutoDismiss);
        hintDialog.show();
    }

    @Override
    public void hideHintDialog() {
        if (hintDialog != null) {
            hintDialog.dismiss();
        }
    }

    @Override
    public void showWaitingDialog() {
        try {
            if (myWaittingDialog == null) {
                myWaittingDialog = new MyWaittingDialog(context);
            }
            myWaittingDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hindWaitingDialog() {
        if (myWaittingDialog != null && myWaittingDialog.isShowing()) {
            myWaittingDialog.dismiss();
        }
        myWaittingDialog = null;
    }

    @Override
    public void toActivity(Class activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }

    @Override
    public void msg(String message, int code) {
        //无需处理 baseActivity中已处理
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void dealScreenOritation() {
        //手动控制
        if(BaseApplication.screenRotationManual){
           if(BaseApplication.screenRotationHorizon){
               //横向显示
              context. setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
           }   else {
               //纵向显示
               context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
           }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mainlib_layout_error) {
            clickError();
        }
    }
}
