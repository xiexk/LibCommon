package com.kun.common.constans;

import com.kun.common.tools.SdCardHelper;

/**
 * Created by admin on 2017/3/9.
 * mainlib 参数 一般保存默认参数
 */

public class CommonConfig {

    public static int HTTP_READ_TIME_OUT = 60;

    public static int HTTP_CONNECT_TIME_OUT = 60;

    public static String BASE_URL = "";

    //默认sd卡地址
    public static String rootPath = SdCardHelper.getExternalSdCardPath() + "/mainlib/";

    //默认保存对象地址
    public static String objectPath = rootPath + "objectCache/";

    //默认保存对象地址
    public static String netCachePath = rootPath + "netCache/";

    //默认保存图片地址
    public static String imagePath = rootPath + "images/";

    //默认保存日志地址
    public static String logPath = rootPath + "logs/";

    //默认sharepreference地址
    public static String SHARE_PREFERENCE_FILE_NAME = "sharepreferenceudDefault";
    /**
     * debug模式tag
     */
    public static final String APP_DEBUG = "appDebug";
    /**
     * 手动控制旋转tag 默认false
     */
    public static final String SCREEN_ROTATION_MANUAL = "screenRotationManual";
    /**
     * 屏幕是否横向 默认横向 仅当手动控制旋转为true时有效
     */
    public static final String SCREEN_ROTATION_HORIZON = "screenRotationHorizon";

    /**
     * 默认地址修改后需要重新设置
     */
    public static void resetPath() {
        objectPath = rootPath + "objectCache/";

        netCachePath = rootPath + "netCache/";

        imagePath = rootPath + "images/";

        logPath = rootPath + "logs/";

    }
}
