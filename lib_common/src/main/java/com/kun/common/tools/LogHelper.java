package com.kun.common.tools;

import com.kun.common.BuildConfig;

import android.util.Log;


public class LogHelper {

  public static void i(String tag, String message) {
    if (BuildConfig.DEBUG) {
      i(tag,message,false);
    }
  } public static void i(String tag, String message,boolean urlDecode) {
    if (BuildConfig.DEBUG) {
      log(tag,message,urlDecode);
    }
  }

  public static void e(String tag, String message) {
    if (BuildConfig.DEBUG) {
      Log.e(tag, message);
    }
  }

  public static void e(String tag, String message, Throwable throwble) {
    if (BuildConfig.DEBUG) {
      Log.e(tag, message, throwble);
    }
  }

  public static void w(String tag, String message) {
    if (BuildConfig.DEBUG) {
      Log.w(tag, message);
    }
  }

  public static void w(String tag, String message, Throwable throwble) {
    if (BuildConfig.DEBUG) {
      Log.w(tag, message, throwble);
    }
  }

  public static void d(String tag, String message) {
    if (BuildConfig.DEBUG) {
      Log.d(tag, message);
    }
  }

  public static void v(String tag, String message) {
    if (BuildConfig.DEBUG) {
      Log.v(tag, message);
    }
  }

  public static void e(Throwable e) {
    if (BuildConfig.DEBUG) {
      e.printStackTrace();
    }
  }

  public static void p(Object e) {
    if (BuildConfig.DEBUG) {
      System.out.println(e.toString());
    }
  }

  public static void log(String tag,String msg) {
    log(tag, msg,false);
  }

  public static void log(String tag,String msg,boolean urldecode) {
    if(urldecode){
      msg = UrlHelper.urlDecoded(msg);
    }
    try {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0){
          return;}
        int segmentSize = 1024;
        long length = msg.length();
        // 长度小于等于限制直接打印
        if (length <= segmentSize) {
          Log.i(tag, msg);
        } else {
          // 循环分段打印日志
          while (msg.length() > segmentSize) {
            String logContent = msg.substring(0, segmentSize);
            msg = msg.replace(logContent, "");
            Log.i(tag, logContent);
          }
          // 打印剩余日志
          Log.i(tag, msg);
        }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
