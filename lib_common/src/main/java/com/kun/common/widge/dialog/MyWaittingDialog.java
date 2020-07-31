package com.kun.common.widge.dialog;


import com.kun.common.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;


/**
 * Created by Administrator on 2016/7/1.
 * 等待框
 */
public class MyWaittingDialog extends ProgressDialog {

  public MyWaittingDialog(Context context) {
    super(context, R.style.my_progress);
    setCanceledOnTouchOutside(true);
  }

  public void setMyCanceledOnTouchOutside(Boolean a) {
    setCanceledOnTouchOutside(a);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initView();
    initData();
  }

  private void initData() {
  }

  private void initView() {
    setContentView(R.layout.mainlib_layout_progress_dialog);
  }
}
