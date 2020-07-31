package com.kun.app.appupdate;

/**
 * 更新模块的
 */
public class ConfigEventMessageUpdate {
    /**
     * 检查更新
     */
    public static int CHECK_UPDATE=900001;
    /**
     * 检查更新已经有版本信息
     */
    public static int CHECK_UPDATE_HAVE_INFO=900002;
    /**
     * 开始下载apk
     */
    public static int BEGIN_DOWNLOAD_APK=900003;
    /**
     * 下载apk中
     */
    public static int BEGIN_DOWNLOADING_APK=900004;
    /**
     * 下载apk结束
     */
    public static int DOWNLOAD_APK_SUCCESS =900005;
    /**
     * 查更新代码 吐司提示已是最新版本
     */
    public static int CHECK_UPDATE_TOAST=900006;
    /**
     * 通知安装apk
     */
    public static int TO_INSTALL_APK =900007;

    /**
     * 检查安装权限
     */
    public static int REQUEST_INSTALL_APK=900008;

    /**
     * 下载失败
     */
    public static int DOWNLOAD_APK_FAILD =900009;

    /**
     * 开始安装apk
     */
    public static int BEGIN_INSTALL_APK=9000010;

    /**
     *  app自启服务通知 (怕更新失败)
     */
    public static final int EVENTMESSAGE_START_AUTO_APPSTART = 20012;


}
