package com.kun.common.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast的快去替换工具
 */
public class HelperToast {
    private static Toast mToast;
    public static void showToast(Context context, int resId, int duration){
        showToast(context, context.getString(resId), duration);
    }
    public static void showToast(Context context, String msg, int duration) {
        try {
            if (mToast == null) {
                mToast = Toast.makeText(context, msg, duration);
            } else {
                mToast.setText(msg);
            }
            mToast.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
