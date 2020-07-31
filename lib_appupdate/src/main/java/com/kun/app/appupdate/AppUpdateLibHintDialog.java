package com.kun.app.appupdate;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


/**
 * @author kun 提示框 提示购买等
 */
public class AppUpdateLibHintDialog extends Dialog implements View.OnClickListener {

  private TextView title, tv, ok;

  private String str_title, str_content;// 文本内容

  private View.OnClickListener onClickListener;


  /**
   * @param context    上下文
   * @param tv_content 文本内容 String类型 重载构造函数 可以为null(显示默认文案)
   */
  public AppUpdateLibHintDialog(Context context, String tv_content) {
    super(context, R.style.appupdatlib_style_dialog_hint);
    this.str_content = tv_content;
    setCanceledOnTouchOutside(true);
  }


  public AppUpdateLibHintDialog(Context context, String tv_content, View.OnClickListener onClickListener) {
    super(context, R.style.appupdatlib_style_dialog_hint);
    this.str_content = tv_content;
    this.onClickListener = onClickListener;
    setCanceledOnTouchOutside(true);
  }

  public AppUpdateLibHintDialog(Context context, String tv_title, String tv_content,
      View.OnClickListener onClickListener) {
    super(context, R.style.appupdatlib_style_dialog_hint);
    this.str_title = tv_title;
    this.str_content = tv_content;
    this.onClickListener = onClickListener;
    setCanceledOnTouchOutside(true);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.appupdatelib_layout_dialog_hint);
    initView();
    initListener();
    initData();
  }

  private void initView() {
    title =  findViewById(R.id.appupdatelib_dialog_title);
    ok =  findViewById(R.id.appupdatelib_dialog_hint_ok);
    tv =  findViewById(R.id.appupdatelib_dialog_tv);
    if (onClickListener != null) {
      findViewById(R.id.appupdatelib_login_return_dialog_centerview).setVisibility(View.VISIBLE);//显示竖线
      findViewById(R.id.appupdatelib_dialog_hint_cancer).setVisibility(View.VISIBLE);//显示取消按钮
    }
  }

  private void initListener() {

    if (onClickListener != null) {
      ok.setOnClickListener(onClickListener);
      findViewById(R.id.appupdatelib_dialog_hint_cancer).setOnClickListener(this);
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
  }

  @Override
  public void onClick(View v) {
    int i = v.getId();
    if (i == R.id.appupdatelib_dialog_hint_ok) {
      this.dismiss();
    } else if (i == R.id.appupdatelib_dialog_hint_cancer) {
      this.dismiss();
    }
  }
}
