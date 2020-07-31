package com.kun.common.tools.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

/**
 * 权限请求
 */
public class HelperPermision {
    private static String log ="请求权限工具";
    /**
     * 请求sd卡权限
     */
    public static boolean checkSdPermission(Activity activity,int code){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.v(log,"手机版本为：android 6.0以上 需要手动请求sd卡权限");
            int writePermission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (writePermission != PackageManager.PERMISSION_GRANTED) {
                Log.v(log,"手机版本为：android 6.0以上--->未申请--->申请读写权限");
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, code);
                return false;
            }else{
                Log.v(log,"手机版本为：android 6.0以上--->已申请sd卡权限");
                return true;
            }
        }else{
            Log.v(log,"手机版本为：android 6.0以下 无需申请sd卡权限");
            return true;
        }

    }
}
