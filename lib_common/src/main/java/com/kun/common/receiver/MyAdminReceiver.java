 package com.kun.common.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author kun
 * Date: 2020/7/13
 * Time: 17:34
 */
public class MyAdminReceiver extends DeviceAdminReceiver {

    //收到设备管理器激活的广播
    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
    }

    //收到设备管理器取消激活的广播
    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
    }
}