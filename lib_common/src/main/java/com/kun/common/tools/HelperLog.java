package com.kun.common.tools;

import android.text.TextUtils;

import com.kun.common.constans.CommonConfig;


/**
 * 日志
 */
public class HelperLog {
    /**
     * 保存日志
     */
    public static void saveLog(String log) {
        if(TextUtils.isEmpty(log)){
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                String filePath = CommonConfig.logPath + DateHelper.getCurrentDatetring();
                SdCardHelper.createFile(filePath);
                FileHelper.writeFileAppend(filePath, log);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
