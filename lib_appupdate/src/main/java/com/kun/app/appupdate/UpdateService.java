package com.kun.app.appupdate;


import java.io.File;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.kun.app.appupdate.UpdateManager.DownLoadListener;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.lztek.toolkit.Lztek;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import static com.kun.app.appupdate.ConfigEventMessageUpdate.BEGIN_DOWNLOAD_APK;
import static com.kun.app.appupdate.ConfigEventMessageUpdate.BEGIN_INSTALL_APK;
import static com.kun.app.appupdate.ConfigEventMessageUpdate.CHECK_UPDATE;
import static com.kun.app.appupdate.ConfigEventMessageUpdate.CHECK_UPDATE_HAVE_INFO;
import static com.kun.app.appupdate.ConfigEventMessageUpdate.CHECK_UPDATE_TOAST;
import static com.kun.app.appupdate.ConfigEventMessageUpdate.EVENTMESSAGE_START_AUTO_APPSTART;
import static com.kun.app.appupdate.ConfigEventMessageUpdate.TO_INSTALL_APK;
import static com.kun.app.appupdate.ConfigEventMessageUpdate.REQUEST_INSTALL_APK;

/**
 * Created by Administrator on 2016/4/28 0028.
 * 更新软件包服务
 */
public class UpdateService extends Service implements DownLoadListener {

    private static String log = "更新服务";

    public static final int EVENTMESSAGEID_CHECK_QUEST_INSTALL_PERMISSTION = 20001;

    private static final String apkName = "app.apk";

    private VersionInfo info = null;

    private int progress = 0;

    private Handler handler;

    //通知栏的进度条组件
    private static final int NOTIFICATION_ID = 101;

    private Notification notification = null;

    private NotificationManager manager = null;

    private boolean isStop = false;

    private TextView tv2 = null;

    private UpdateManager updateManager;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initHandler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!EventBus.getDefault().isRegistered(this)) {
            Log.i(log, "EventBus注册");
            EventBus.getDefault().register(this);
        }
        Log.i(log, "UpdateService 启动了");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 取消注册EventBus
     */
    private void unRegisterEventBus(){
        if (EventBus.getDefault().isRegistered(this)) {
            Log.i(log, "EventBus已经注册 执行取消注册");
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(EventMessageUpdateApp eventMessage) {
        int id = eventMessage.id;
        Log.i(log, "收到命令："+eventMessage.id);
        //检查更新
        if (id == CHECK_UPDATE) {
            updateManager = new UpdateManager(this);
            updateManager.setDownLoadListener(this);
            updateManager.checkUpdate();
        }//检查更新
        if (id == CHECK_UPDATE_HAVE_INFO) {
            Log.i(log, "收到更新命令");
            updateManager = new UpdateManager(this);
            updateManager.setDownLoadListener(this);
            VersionInfo versionInfo = (VersionInfo) eventMessage.data;
            updateManager.checkUpdate(versionInfo);
        } else if (id == CHECK_UPDATE_TOAST) {
            //查更新代码 吐司提示已是最新版本
            updateManager = new UpdateManager(this, true);
            updateManager.setDownLoadListener(this);
            updateManager.checkUpdate();
        } else if (id == BEGIN_DOWNLOAD_APK) {
            beginDownLoad();
        } else if (id == TO_INSTALL_APK) {
            //安装apk包 （一般是申请安装软件权限后调用 已有权限不会调用）
            installApk(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(log, "UpdateService 结束了");
       unRegisterEventBus();
    }

    /**
     * initHandler
     */
    @SuppressLint("HandlerLeak")
    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        upDateNotify(false, progress);
                        break;
                    case 2:
                        checkInstallPermission();
                        break;
                    case 3:
                        upDateNotify(true, progress);
                    default:
                        break;
                }
            }
        };
    }


    private void checkInstallPermission() {
        Log.i(log, "检查软件安装权限");
        if (Build.VERSION.SDK_INT >= 26) {
            Log.i(log, "android 版本大于8.0");
            boolean b = getPackageManager().canRequestPackageInstalls();
            if (b) {
                Log.i(log, "android 版本大于8.0，已有安装软件权限，开始安装软件");
                installApk(this);
            } else {
                Log.i(log, "android 版本大于8.0，没有安装软件权限，开始请求软件安装权限");
                EventMessageUpdateApp eventMessageUpdateApp = new EventMessageUpdateApp(REQUEST_INSTALL_APK, null);
                EventBus.getDefault().post(eventMessageUpdateApp);
            }
        } else {
            Log.i(log, "已有安装软件权限，开始安装软件");
            installApk(this);
        }
    }

    /**
     * install update apk
     */
    public static void installApk(Context context) {
        File apkfile = new File(ConfigAPPUpdateLib.UPDATE_PATH + apkName);
        if (!apkfile.exists()) {
            return;
        }
        EventBus.getDefault().post( new EventMessageUpdateApp(BEGIN_INSTALL_APK, apkName));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, ConfigAPPUpdateLib.PACKAGE_NAME + ".fileProvider", apkfile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        }

        Log.i(log, "发送自动启动通知 怕更新失败");
        EventMessageUpdateApp eventMessageUpdateApp = new EventMessageUpdateApp(EVENTMESSAGE_START_AUTO_APPSTART, null);
        EventBus.getDefault().post(eventMessageUpdateApp);

        intent.putExtra("IMPLUS_INSTALL", "SILENT_INSTALL");
        context.startActivity(intent);


        try {
            //拍档机子自动安装
            Lztek lztek = Lztek.create(context);
            lztek.installApplication(apkfile.getAbsolutePath());
        } catch (Exception |NoClassDefFoundError e) {
            e.printStackTrace();
        }

    }

    /**
     * download the new apk thread
     */
    private Runnable downApkRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(log,"开始下载apk");
            String apkFile = ConfigAPPUpdateLib.UPDATE_PATH + apkName;
            File ApkFile = FileUtil.createFile(apkFile);
            ApkFile.delete();
            if (info != null && info.apkversionUrl != null) {
                dowmLoadApk(info.apkversionUrl, apkFile);
            } else {
                EventBus.getDefault().post(new EventMessageUpdateApp<>(ConfigEventMessageUpdate.DOWNLOAD_APK_FAILD, "下载地址为空"));
            }
        }
    };

    private void dowmLoadApk(String url, String path) {
        Log.i(log, "开始下载apk包");
        BaseDownloadTask baseDownloadTask = com.liulishuo.filedownloader.FileDownloader.getImpl().create(url)
                .setPath(path, false)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setTag(url)
                .setAutoRetryTimes(5)
                .setListener(new ApkDownloadListener() {
                    @Override
                    protected void completed(BaseDownloadTask task) {
                        super.completed(task);
                        progress = 100;
                        handler.sendEmptyMessage(1);
                        handler.sendEmptyMessage(2);//下载完成
                        EventBus.getDefault().post(new EventMessageUpdateApp<>(ConfigEventMessageUpdate.DOWNLOAD_APK_SUCCESS, null));
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        super.error(task, e);
                        handler.sendEmptyMessage(3);//下载失败
                        EventBus.getDefault().post(new EventMessageUpdateApp<>(ConfigEventMessageUpdate.DOWNLOAD_APK_FAILD, e.toString()));
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.progress(task, soFarBytes, totalBytes);
                        progress = (int) (((float) soFarBytes / totalBytes) * 100);  //为了每个百分比发送一次通知
                        handler.sendEmptyMessage(1);//下载进度
                        ModelDownLoadingInfo modelDownLoadingInfo = new ModelDownLoadingInfo();
                        modelDownLoadingInfo.url=task.getUrl();
                        modelDownLoadingInfo.progress = progress;
                        modelDownLoadingInfo.speed = task.getSpeed();
                        EventBus.getDefault().post(new EventMessageUpdateApp<>(ConfigEventMessageUpdate.BEGIN_DOWNLOADING_APK, modelDownLoadingInfo));
                    }
                });
        baseDownloadTask.start();

    }


    /**
     * 弹出通知栏下载框
     */
    private void progressNotify() {

        NotificationCompat.Builder builder;
        RemoteViews rvMain = new RemoteViews(this.getPackageName(), R.layout.appupdatelib_notification);

        builder = new NotificationCompat.Builder(this, ConfigAPPUpdateLib.channelId)
                .setContent(rvMain)
                .setOnlyAlertOnce(true)
                .setSmallIcon(R.drawable.icon_notify)
                .setTicker("软件升级");

        notification = builder.build();
        notification.contentView.setProgressBar(R.id.progressBar_notify, 100, 0, false);
        notification.contentView.setTextViewText(R.id.textView_notify, "软件升级包" + progress + "%");
        manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, notification);
    }

    /**
     * update com.yingjie.kxx.app.kxxfind.view
     */
    private void upDateNotify(boolean failed, int progress) {
        notification.contentView.setProgressBar(R.id.progressBar_notify, 100, progress,
                false);
        if (failed) {
            notification.contentView.setTextViewText(R.id.textView_notify, "下载失败...");
        } else {
            notification.contentView.setTextViewText(R.id.textView_notify, "软件升级包"
                    + progress + "%");
        }
        manager.notify(NOTIFICATION_ID, notification);
    }


    /**
     * START DOWNLOAD THREAD
     */
    private void startDownLoad() {
        ThreadUtil.threadPool.execute(downApkRunnable);
    }


    public void beginDownLoad() {
        Log.i(log,"手动下载apk命令");
        progressNotify();
        startDownLoad();
    }

    @Override
    public void getVersionInfo(VersionInfo versionInfo) {
        this.info = versionInfo;
    }
}
