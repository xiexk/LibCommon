package com.kun.common.tools;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HelperCmd {

    private static String log = "cmd命令";


    /**
     * 设备是否可以执行root 指令
     */
    public static boolean isRootAndCanExec() {

        //android 8.0以上版本
        String cmd = "su";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            cmd = "/system/bin/sh";
        }
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            process.getOutputStream().write("exit\n".getBytes());
            process.getOutputStream().flush();
            int i = process.waitFor();
            if (0 == i) {
                process = Runtime.getRuntime().exec(cmd);
                return true;
            }

        } catch (Exception e) {
            return false;
        }
        return false;

    }

    /**
     * 重启设备
     */
    public static boolean reBootDriver() {
        Log.i(log, "重启设备");
        try {
            String cmd="su -c reboot";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                cmd = "/system/bin/sh -c reboot";
            }
            String result = execCmd(cmd);
            LogHelper.i(log, "执行重启设备返回：" + result);
            if (!TextUtils.isEmpty(result) && result.contains("Permission denied")) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 关机设备
     */
    public static void shutDownDriver() {
        try {
            String cmd="su -c shutdown";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                cmd = "/system/bin/sh -c shutdown";
            }
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String execCmd(String cmd) {
        DataOutputStream dos = null;
        String result = "";
        String lastline = " ";
        try {
            // 经过Root处理的android系统即有su命令
            Process process = Runtime.getRuntime().exec(cmd);
            //get the err line

            InputStream stderr = process.getErrorStream();
            InputStreamReader isrerr = new InputStreamReader(stderr);
            BufferedReader brerr = new BufferedReader(isrerr);

            //get the output line
            InputStream outs = process.getInputStream();
            InputStreamReader isrout = new InputStreamReader(outs);
            BufferedReader brout = new BufferedReader(isrout);
            String errline = null;


            // get the whole error message
            String line = "";

            while ((line = brerr.readLine()) != null) {
                result += line;
                result += "/n";
            }

            if (result != "") {
                // put the result string on the screen
                Log.i(log, " the str error message " + result.toString());
                return result;
            }

            // get the whole standard output string
            while ((line = brout.readLine()) != null) {
                lastline = line;
                result += line;
                result += "/n";
            }
            if (result != "") {
                // put the result string on the screen
                Log.i(log, " the standard output message " + lastline.toString());
            }
        } catch (Exception t) {
            t.printStackTrace();
            lastline = lastline+t.getMessage();
        }
        return lastline.toString();
    }

}
