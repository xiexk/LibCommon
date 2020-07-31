package com.kun.common;

import com.kun.common.constans.CommonConfig;
import com.kun.common.data.sharepreference.SharePreferenceHelper;
import com.kun.common.tools.FileDownLoadHelper;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;


/**
 * Created by Administrator on 2018/9/7.
 * maillib 的 application
 */

public class BaseApplication extends Application {

    public static Application application;

    /**
     * iconfont字体文件 淘宝图标库
     */
    public static Typeface iconfont;
    /**
     * 是否debug
     */
    public static boolean debug ;
    /**
     * 是否手动控制旋转
     */
    public static boolean screenRotationManual ;
    /**
     * 屏幕是否横向显示 当手动控制屏幕旋转为true时有效
    */
    public static boolean screenRotationHorizon ;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        debug=getDebug();
        screenRotationManual=getScreenRotationManual();
        screenRotationHorizon=getScreenRotationHorizon();
        //初始化imageloader
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
        initFileDownLoader();
    }


    /**
     * 设置debug
     */
    public static void setDebug(boolean debug) {
        BaseApplication.debug = debug;
        SharePreferenceHelper sharePreferenceHelper= new SharePreferenceHelper(
                BaseApplication.application);
        sharePreferenceHelper.saveBoolean(CommonConfig.APP_DEBUG,debug);
    }

    /**
     * 获取是否debug
     */
    public static boolean getDebug(){
        SharePreferenceHelper sharePreferenceHelper= new SharePreferenceHelper(
                BaseApplication.application);
        return sharePreferenceHelper.getBoolean(CommonConfig.APP_DEBUG);
    }

    /**
     * 设置手动控制旋转
     */
    public static void setScreenRotationManual(boolean screenRotationManual) {
        BaseApplication.screenRotationManual = screenRotationManual;
        SharePreferenceHelper sharePreferenceHelper= new SharePreferenceHelper(
                BaseApplication.application);
        sharePreferenceHelper.saveBoolean(CommonConfig.SCREEN_ROTATION_MANUAL,screenRotationManual);
    }

    /**
     * 获取是否手动控制旋转
     */
    public static boolean getScreenRotationManual(){
        SharePreferenceHelper sharePreferenceHelper= new SharePreferenceHelper(
                BaseApplication.application);
        return sharePreferenceHelper.getBoolean(CommonConfig.SCREEN_ROTATION_MANUAL);
    }
    /**
     * 设置屏幕是否横向显示
     */
    public static void setScreenRotationHorizon(boolean screenRotationHorizon) {
        BaseApplication.screenRotationHorizon = screenRotationHorizon;
        SharePreferenceHelper sharePreferenceHelper= new SharePreferenceHelper(
                BaseApplication.application);
        sharePreferenceHelper.saveBoolean(CommonConfig.SCREEN_ROTATION_HORIZON,screenRotationHorizon);
    }

    /**
     * 获取屏幕是否横向显示
     */
    public static boolean getScreenRotationHorizon(){
        SharePreferenceHelper sharePreferenceHelper= new SharePreferenceHelper(
                BaseApplication.application);
        return sharePreferenceHelper.getBoolean(CommonConfig.SCREEN_ROTATION_HORIZON,true);
    }

    private void initFileDownLoader() {
        FileDownloadLog.NEED_LOG = false;
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15_000)
                        .readTimeout(15_000)
                ))
                .commit();
        FileDownLoadHelper.cleanAllTaskData();
    }

    public static Typeface getIconfont(Context context) {
        if (iconfont != null) {
            return iconfont;
        } else {
            iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");
        }
        return iconfont;
    }
}