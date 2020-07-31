package com.kun.common.tools;

import static android.content.Context.ACTIVITY_SERVICE;

import android.app.ActivityManager;
import android.content.Context;

public class ServiceHelper {
  /**
   * 判断服务是否在运行
   * @param context
   * @param serviceName
   * @return
   * 服务名称为全路径 例如com.ghost.WidgetUpdateService
   */
  public static boolean isRunService(Context context,String serviceName) {
    ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
    for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
      if (serviceName.equals(service.service.getClassName())) {
        return true;
      }
    }
    return false;
  }
}
