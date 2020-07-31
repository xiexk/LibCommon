package com.kun.app.appupdate;

import static com.kun.app.appupdate.ConfigAPPUpdateLib.APP_CHECK_UPDATE_URL;
import static com.kun.app.appupdate.ConfigEventMessageUpdate.BEGIN_DOWNLOAD_APK;

import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

/**
 * APK更新管理类
 *
 * @author Royal
 */
public class UpdateManager {

  private static String log = "更新控制器";

  // 上下文对象
  private Context mContext;

  // 更新版本信息对象
  private VersionInfo info = null;

  private boolean showToast = false;

  /**
   * 参数为Context(上下文activity)的构造函数
   */
  public UpdateManager(Context context) {
    this.mContext = context;
  }

  /**
   * 参数为Context(上下文activity)的构造函数 showHint 提示已是最新版本
   */
  public UpdateManager(Context context, boolean showToast) {
    this.mContext = context;
    this.showToast = showToast;
  }

  /**
   * 检查版本更新 已经下载到更新文件
   */
  public void checkUpdate(VersionInfo versionInfo){
    this.info=versionInfo;
    handler.sendEmptyMessage(3);
  }

  public void checkUpdate() {  //没有传入handler 在主页面显示
    Thread thread = new Thread() {
      @Override
      public void run() {
        super.run();
        info = getVersionInfoFromServer();
        handler.sendEmptyMessage(3);
      }
    };
    thread.start();
  }

  /**
   * 从服务端获取版本信息
   */
  private VersionInfo getVersionInfoFromServer() {
    VersionInfo info = null;
    try {
      URL url = new URL(APP_CHECK_UPDATE_URL);
      HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
      urlConn.setConnectTimeout(6000);
      info = APPUpdateTool.getUpdateInfo(urlConn.getInputStream());
      urlConn.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return info;
  }



  /**
   * 声明一个handler来跟进进度条
   */
  @SuppressLint("HandlerLeak")
  private Handler handler = new Handler() {
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case 3:
          Log.i(log, "比较版本信息");
          // 从服务端获取版本信息
          if (info != null) {
            try {
              // 获取当前软件包信息
              PackageInfo pi = mContext.getPackageManager().getPackageInfo(ConfigAPPUpdateLib.PACKAGE_NAME, 0);
              // 当前软件版本号
              int versionCode = pi.versionCode;
              if (versionCode < Integer.valueOf(info.apkversionId)) {
                // 如果当前版本号小于服务端版本号,则弹出提示更新对话框
                Log.i(log, "比较版本信息 当前版本号小于服务端版本号");
                showUpdateDialog();
              } else {
                if (showToast) {
                  Toast.makeText(mContext, "已是最新版本", Toast.LENGTH_SHORT).show();
                }
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          } else {
            Log.e(log, "版本信息为空");
          }
          break;
        default:
          break;
      }
    }
  };
  /**
   * 提示更新对话框
   */

  private void showUpdateDialog() {

//    Intent intent=new Intent(mContext,UpdateDialogActivity.class);
//    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
//    intent.putExtra("data",info);
//    mContext.startActivity(intent);
    Log.i(log, "发送下载apk通知");
    if(downLoadListener!=null){
      downLoadListener.getVersionInfo(info);
    }
    EventMessageUpdateApp eventMessageUpdateApp=new EventMessageUpdateApp(BEGIN_DOWNLOAD_APK,null);
    EventBus.getDefault().post(eventMessageUpdateApp);
  }

  private DownLoadListener downLoadListener;

  public void setDownLoadListener(DownLoadListener downLoadListener) {
    this.downLoadListener = downLoadListener;
  }

  public interface DownLoadListener{
    void getVersionInfo(VersionInfo versionInfo);
  }
}