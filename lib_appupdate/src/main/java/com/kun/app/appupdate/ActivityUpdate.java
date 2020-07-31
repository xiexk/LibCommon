package com.kun.app.appupdate;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2019/4/9.
 */

public class ActivityUpdate extends Activity {
  private String log=ActivityUpdate.class.getName();
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.i(log,"ActivityUpdate 启动了");
    Intent service = new Intent(this, UpdateService.class);
    this.startService(service);
    Log.i(log,"ActivityUpdate 结束了");
    finish();
    //通过淡入淡出的效果关闭和显示Activity
    overridePendingTransition(0,0);
   }
}
