package com.kun.common.tools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * 广播工具
 */
public class HelperReceiverHelper {
    /**
     * 检测广播是否注册
     */
    public static boolean checkIsRegisterReceiver(Context context,String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryBroadcastReceivers(intent, 0);
        if (resolveInfos != null && !resolveInfos.isEmpty()) {
            //查询到相应的BroadcastReceiver
            return true;
        }
        return false;
    }


}
