package com.kun.common.net;

import android.os.Environment;
import android.util.Log;

import com.kun.common.tools.DateHelper;
import com.kun.common.tools.SdCardHelper;
import com.kun.common.tools.UrlHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2018/12/13.
 * 网络日志
 */

public class HttpLog implements HttpLoggingInterceptor.Logger {
    public static boolean showLog = false;

    @Override
    public void log(String msg) {
        msg = UrlHelper.urlDecoded(msg);
        try {
            if (showLog) {
                String tag = "网络日志";
                if (tag == null || tag.length() == 0
                        || msg == null || msg.length() == 0){
                    return;}
                int segmentSize = 1024;
                long length = msg.length();
                // 长度小于等于限制直接打印
                if (length <= segmentSize) {
                    Log.i(tag, msg);
                    if(msg.startsWith("Date:")){
                       String[] date=msg.split("Date: ");
                       if(date.length==2){
                           Date date1=new Date(date[1]);
                           Log.i(tag, date1.toString());
                           Log.i(tag,  DateHelper.convertToString(date1,DateHelper.TIME_FORMAT));
                       }
                    }
                    saveLog(msg);
                } else {
                    // 循环分段打印日志
                    while (msg.length() > segmentSize) {
                        String logContent = msg.substring(0, segmentSize);
                        msg = msg.replace(logContent, "");
                        Log.i(tag, logContent);
                        saveLog(logContent);
                    }
                    // 打印剩余日志
                    Log.i(tag, msg);
                    saveLog(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean saveLogToLocal = false;

    public static void saveLog(String conent) {
        if (saveLogToLocal) {

            String time = new Date().getDay() + "";
            String fileName = "/sdcard/crash/crash-" + time + ".log";
            SdCardHelper.createFile(fileName);

            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(fileName, true)));
                out.write(conent + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
