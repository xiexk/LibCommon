package com.kun.app.appupdate;



import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import static com.kun.app.appupdate.ConfigEventMessageUpdate.BEGIN_DOWNLOAD_APK;

/**
 * Created by Administrator on 2019/4/9.
 */

public class UpdateDialogActivity extends Activity implements OnClickListener{
  private TextView title, tv, ok;

  private  VersionInfo info;

  private View.OnClickListener onClickListener;
  /**
   * 提示信息
   */
  private String tipContent="";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if(!EventBus.getDefault().isRegistered(this)){
      EventBus.getDefault().register(this);
    }
    //去除这个Activity的标题栏
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    info= (VersionInfo) getIntent().getSerializableExtra("data");
    setContentView(R.layout.appupdatelib_layout_dialog_hint);
    initView();
    initListener();
    initData();
  }


  private void initView() {
    title =  findViewById(R.id.appupdatelib_dialog_title);
    ok =  findViewById(R.id.appupdatelib_dialog_hint_ok);
    tv =  findViewById(R.id.appupdatelib_dialog_tv);
    //显示竖线
    findViewById(R.id.appupdatelib_login_return_dialog_centerview).setVisibility(View.VISIBLE);
    //显示取消按钮
    findViewById(R.id.appupdatelib_dialog_hint_cancer).setVisibility(View.GONE);
  }

  private void initListener() {
    findViewById(R.id.appupdatelib_dialog_hint_cancer).setOnClickListener(this);
    ok.setOnClickListener(this);
  }

  private void initData() {
    tipContent="更新版本"+info.apkVersion+"中...";
      title.setText("更新提示");
      tv.setText(tipContent);
      EventMessageUpdateApp eventMessageUpdateApp=new EventMessageUpdateApp(BEGIN_DOWNLOAD_APK,null);
    EventBus.getDefault().post(eventMessageUpdateApp);
  }

  @Override
  public void onClick(View v) {
    int i = v.getId();
    if (i == R.id.appupdatelib_dialog_hint_ok) {

      finish();
    } else if (i == R.id.appupdatelib_dialog_hint_cancer) {
      finish();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    hideNavigation();
  }
  @Subscribe
  public void eventMessage(EventMessageUpdateApp eventMessageUpdateApp){
    try{
    if(eventMessageUpdateApp.id==ConfigEventMessageUpdate.BEGIN_DOWNLOADING_APK){
      if(tv!=null){
        ModelDownLoadingInfo modelDownLoadingInfo= (ModelDownLoadingInfo) eventMessageUpdateApp.data;
        tv.setText(tipContent+"\n"+"进度："+modelDownLoadingInfo.progress+"%\n速度："+modelDownLoadingInfo.speed+"k");
      }
    }if(eventMessageUpdateApp.id==ConfigEventMessageUpdate.DOWNLOAD_APK_SUCCESS){
      if(tv!=null){
        tv.setText(tipContent+"\n"+"下载完毕 准备安装中...");
      }
    }}catch (Exception e){
      e.printStackTrace();
    }
  }

  /**
   * 全屏，自动隐藏导航栏和虚拟按键
   *
   */
  public  void hideNavigation() {
    View decorView = this.getWindow().getDecorView();
    decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if(EventBus.getDefault().isRegistered(this)){
      EventBus.getDefault().unregister(this);
    }
  }
}
