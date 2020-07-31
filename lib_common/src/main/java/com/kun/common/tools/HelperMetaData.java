package com.kun.common.tools;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2019/4/28.
 */

public class HelperMetaData {

  /**
   * 获取字符串类型
   */
  public static String getMetaData(Context mContext,String key) {
    ApplicationInfo appInfo = null;
    try {
      appInfo = mContext.getApplicationContext()
          .getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
      if (appInfo != null && appInfo.metaData != null) {
        String value = appInfo.metaData.getString(key);
        return value;
      } else {
       LogHelper.i("MetaDataHelper错误","没有找到key为："+key+"的值");
        return "";
      }
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return "";
  }
  /**
   * 获取Boolean类型
   */
  public static boolean getMetaDataBoolean(Context mContext,String key) {
    ApplicationInfo appInfo = null;
    try {
      appInfo = mContext.getApplicationContext()
          .getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
      if (appInfo != null && appInfo.metaData != null) {
        boolean value = appInfo.metaData.getBoolean(key);
        return value;
      } else {
       LogHelper.i("MetaDataHelper错误","没有找到key为："+key+"的值");
        return false;
      }
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return false;
  }
}
