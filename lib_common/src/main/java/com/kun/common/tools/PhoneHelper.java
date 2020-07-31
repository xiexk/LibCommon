package com.kun.common.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import com.kun.common.BaseApplication;
import com.kun.common.data.sharepreference.SharePreferenceHelper;
import com.kun.common.receiver.MyAdminReceiver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import io.reactivex.ObservableEmitter;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.POWER_SERVICE;


/**
 * 手机相关的工具类
 */
public class PhoneHelper {
    private static String log = "PhoneHelper";

    public static int SCREEN_HEIGHT = 360;//屏幕高度默认1080

    public static int SCREEN_WIDTH = 1920;//屏幕宽度默认1920

    //................................................手机网络..............................................

    /**
     * WIFI网络
     **/
    public static int WIFI = 1;

    /**
     * wap网络
     **/
    public static int CMWAP = 2;

    /**
     * net网络
     **/
    public static int CMNET = 3;


    /**
     * 获取当前的网络状态 -1：没有网络 1：WIFI网络2：wap网络3：net网络
     */
    public static int getAPNType(Context context) {
        int netType = -1;
        try {
            ConnectivityManager connMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo == null) {
                return netType;
            }
            int nType = networkInfo.getType();
            if (nType == ConnectivityManager.TYPE_MOBILE) {
                if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                    netType = CMNET;
                } else {
                    netType = CMWAP;
                }
            } else if (nType == ConnectivityManager.TYPE_WIFI) {
                netType = WIFI;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return netType;
        }
        return netType;
    }


    /**
     * 检查是否有网络(网卡有连接 当不一定能访问外网)
     */
    public static boolean isNetworkAvailable(Context context) {

        NetworkInfo info = getNetworkInfo(context);
        return info != null && info.isAvailable();
    }

    @SuppressLint("MissingPermission")
    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * 获取当前网络连接的类型信息
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 判断MOBILE网络是否可用
     */
    public boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 检测网络是否可用
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission")
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 判断环境是否 wifi
     */
    public static boolean isWiFiActive(Context mContext) {
        Context context = mContext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            @SuppressLint("MissingPermission")
            NetworkInfo[] info = connectivity.getAllNetworkInfo();

            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI")
                            && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

//................................................手机网络..............................................
//................................................定位服务..............................................

    /**
     * 判断定位服务是否开启
     *
     * @param
     * @return true 表示开启
     */
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
//................................................定位服务..............................................

//................................................手机设备信息..............................................


    /**
     * 获取本机的IMEI 唯一id
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceId(Context context) {
        String pre_key = "device_id";//sharepreference 保存设备id的key
        UUID uuid;
        SharePreferenceHelper sharePreferenceHelper = new SharePreferenceHelper(context);

        String id = sharePreferenceHelper.getValue(pre_key);
        if (id != null) {
            return id;
        } else {
            try {
                String deviceId = "";
                int permi = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
                if (permi == PackageManager.PERMISSION_GRANTED) {//是否有获取设备id权限 0已授权 -1未授权
                    deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
                            .getDeviceId();//862585034551579
                }
                if (!TextUtils.isEmpty(deviceId)) {
                    uuid = UUID.nameUUIDFromBytes(deviceId.getBytes("utf8"));
                } else {
                    String androidId = Settings.Secure
                            .getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    if (!"9774d56d682e549c".equals(androidId)) {//设备产商bug 所有设备相同android id 有bug就使用uuid生成
                        uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                    } else {
                        uuid = UUID.randomUUID();
                    }
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            sharePreferenceHelper.saveData(pre_key, uuid.toString());
        }

        return sharePreferenceHelper.getValue(pre_key);
    }

    /**
     * 返回SIM卡运营商所在国家的MCC+MNC.
     */
    public static String getMCC(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // 返回当前手机注册的网络运营商所在国家的MCC+MNC. 如果没注册到网络就为空.
        String networkOperator = tm.getNetworkOperator();
        if (!TextUtils.isEmpty(networkOperator)) {
            return networkOperator;
        }
        // 返回SIM卡运营商所在国家的MCC+MNC. 5位或6位. 如果没有SIM卡返回空
        return tm.getSimOperator();
    }

    /**
     * 获取本机的号码
     *
     * @param activity
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getPhoneNumber(Activity activity) {
        TelephonyManager manager = (TelephonyManager) activity
                .getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getLine1Number();
    }

//................................................手机设备信息结束..............................................

    //................................................手机亮度..............................................

    /**
     * 判断是否开启了自动亮度调节
     */
    public static boolean isAutoBrightness(ContentResolver aContentResolver) {
        boolean automicBrightness = false;
        try {
            automicBrightness = Settings.System.getInt(aContentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return automicBrightness;
    }

    /**
     * 获取屏幕的亮度
     */
    public static int getScreenBrightness(Context activity) {
        int nowBrightnessValue = 0;
        ContentResolver resolver = activity.getContentResolver();
        try {
            nowBrightnessValue = Settings.System.getInt(
                    resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }

    /**
     * 设置亮度
     */
    public static void setBrightness(Activity activity, int brightness) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 停止自动亮度调节
     */
    public static void stopAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    /**
     * 开启亮度自动调节
     */
    public static void startAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    /**
     * 保存亮度设置状态
     */
    public static void saveBrightness(ContentResolver resolver, int brightness) {
        Uri uri = Settings.System
                .getUriFor("screen_brightness");
        Settings.System.putInt(resolver, "screen_brightness",
                brightness);
        resolver.notifyChange(uri, null);
    }

    /**
     * 更改背光亮度
     */
    public static void toggleBrightness(Activity activity, int brightness) {
        // 获取亮度值
        brightness = getScreenBrightness(activity);
        // 是否亮度自动调节，如果是则关闭自动调节
        boolean isAutoBrightness = isAutoBrightness(activity
                .getContentResolver());
        if (isAutoBrightness) {
            stopAutoBrightness(activity);
        }
        brightness += 50;// 按自己的需求设置
        // 设置亮度
        setBrightness(activity, brightness);

        if (brightness > 255) {
            // 亮度超过最大值后设置为自动调节
            startAutoBrightness(activity);
            brightness = 50;// 按自己的需求设置
        }
        // 保存设置状态
        saveBrightness(activity.getContentResolver(), brightness);
    }

//................................................手机亮度结束..............................................

    //................................................手机屏幕尺寸和转换..............................................


    public static void setScreenHeightandWidth(Activity c) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        c.getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        LogHelper.i(log, "屏幕大小：宽" + outMetrics.widthPixels + "x高" + outMetrics.heightPixels);
        SCREEN_HEIGHT = outMetrics.heightPixels;
        SCREEN_WIDTH = outMetrics.widthPixels;
        LogHelper.i(log, "屏幕大小默认值：" + SCREEN_WIDTH + "x" + SCREEN_HEIGHT);
    }

    /**
     * 是否与手机原始分辨率相同  相同：true 不同：false
     */
    public static boolean thesameWithPhoneScreenSize(int width, int height) {
        if (width <= 0 || height <= 0) {
            LogHelper.i(log, "宽度或高度需大于零 " + width + "x" + height);
            return true;
        }
        if (width == SCREEN_WIDTH && height == SCREEN_HEIGHT) {
            LogHelper.i(log, "与屏幕原本大小一致 " + width + "x" + height);
            return true;
        }
        return false;
    }


    // 获取CPU名字
    public static String getCpuName() {
        String cpuName = "";
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text;

            while ((text = br.readLine()) != null) {

                if (text.startsWith("Hardware")) {
                    cpuName = text.split(":")[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cpuName;
    }

    /**
     * @return 获取状态栏高度
     */
    public static int getStatHeight(Activity context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * dip值转换到px值
     */

    public static int dip2px(Context c, int dipValue) {
        float scale = c.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px转成为 dip
     */
    public static float px2dip(Context context, float pxValue) {
        float fdp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                pxValue, context.getResources().getDisplayMetrics());
        return fdp;
    }

    //................................................手机屏幕尺寸和转换结束..............................................

    // ................................................手机亮度........................................................
    //单页亮度

    public static void changeWindowBrightness(Activity activity, float a) {
        try {
            // 获取系统亮度
            float systembrightness = Settings.System
                    .getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            Log.v("PhoneHelper", "系统亮度" + systembrightness);
            float b = systembrightness / 255f;
            if (b > a) {//
                Window window = activity.getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                Log.v("PhoneHelper", "window亮度" + lp.screenBrightness);
                lp.screenBrightness = a;
                window.setAttributes(lp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void returnSystemWindowBrightness(Activity activity) {
        try {

            Window window = activity.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Log.v("PhoneHelper", "window亮度" + lp.screenBrightness);
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
            window.setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param activity MInu系统跳转到权限界面
     */
    public static void toMiuSecurity(Activity activity) {
        PackageManager pm = activity.getPackageManager();
        PackageInfo info = null;
        try {
            info = pm.getPackageInfo(activity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        i.setClassName("com.android.settings", "com.miui.securitycenter.permission.AppPermissionsEditor");
        i.putExtra("extra_package_uid", info.applicationInfo.uid);
        try {
            activity.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(x.app(), "只有MIUI才可以设置哦", Toast.LENGTH_SHORT).show();
        }
    }

    public static void getKeyboardListener(Activity activity, View rootView,
                                           KeyboardResultListener keyboardResultListener) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int softKeyboardHeight = 100;
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
                int heightDiff = rootView.getBottom() - r.bottom;
                int displayHight = r.bottom - r.top;
                //获得屏幕整体的高度
                int hight = rootView.getHeight();
                boolean mKeyboardUp = heightDiff > softKeyboardHeight * dm.density;
                if (mKeyboardUp) {

                    //键盘弹出
                    //Toast.makeText(getApplicationContext(), "键盘弹出", Toast.LENGTH_SHORT).show();
                    //获得键盘高度
                    int keyboardHeight = hight - displayHight - PhoneHelper.getStatHeight(activity);
                    keyboardResultListener.keyBoardShow(keyboardHeight);
                } else {
                    keyboardResultListener.keyBoardhide();
                    //键盘收起
                    //Toast.makeText(getApplicationContext(), "键盘收起", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public interface KeyboardResultListener {
        /**
         * 显示软键盘
         *
         * @param height 软键盘高度
         */
        void keyBoardShow(int height);

        /**
         * 隐藏软键盘
         */
        void keyBoardhide();
    }

    /**
     * 复制到剪切板
     */
    public static void copyToClipboard(Context context, CharSequence content) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText(null, content));//参数一：标签，可为空，参数二：要复制到剪贴板的文本
            if (clipboard.hasPrimaryClip()) {
                clipboard.getPrimaryClip().getItemAt(0).getText();
            }
        }
    }

    /**
     * 获取 Meta内容 Application.xml中获取
     */
    public static String getAppMeta(String metaName, Context appContext) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = appContext.getPackageManager()
                    .getApplicationInfo(appContext.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return appInfo.metaData.getString(metaName);
    }

    /**
     * 获取软件包信息
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        // 获取当前软件包信息
        try {
            pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi;
    }


    /**
     * 获取IP
     */
    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("找不到IP地址", ex.toString());
        }
        return null;
    }

    /**
     * 获取当前时区
     */
    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        String strTz = tz.getDisplayName(false, TimeZone.SHORT);
        return strTz;

    }

    /**
     * 获取设备SN号
     */
    public static String getDrivetSN() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return Build.SERIAL;
        } else {
            if (ActivityCompat.checkSelfPermission(BaseApplication.application, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            return Build.getSerial();
        }

    }


    /**
     * 获取当前内存信息对象
     *
     * @return 当前内存信息对象。
     * <p>
     * ActivityManager.MemoryInfo memoryInfo = DeviceInfoUtils.getMemoryInfo();
     * if (memoryInfo != null) {
     * totalMemory = memoryInfo.totalMem; //总内存字节
     * availableMemory = memoryInfo.availMem;//可用内存字节
     * }
     */
    public static ActivityManager.MemoryInfo getMemoryInfo(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        return mi;
    }

    /**
     * 获取mac地址
     *
     * @param context
     * @return
     */
    public static String getMac(Context context) {
        return MacUtil.getMac(context);
    }


    /**
     * 获取手机型号
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机系统版本
     */
    public static String getVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取系统总存储大小
     *
     * @return
     */
    public static String getTotalSystemSize() {
        StatFs statFs = getSystemStatFs();
        if (statFs != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                //总存储空间字节
                return statFs.getTotalBytes() + "";
            }
        }
        return "";
    }

    /**
     * 获取系统可用存储大小
     */
    public static String getFreeSystemSize() {
        StatFs statFs = getSystemStatFs();
        if (statFs != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                //总存储空间字节
                return statFs.getFreeBytes() + "";
            }
        }
        return "";
    }

    /**
     * 获取系统存储空间信息
     *
     * @return LogUtils.i(" ", " block大小 : " + blockSize + ", block数目 : " + blockCount + ", 总大小 : " + blockSize * blockCount / 1024 + " KB ");
     * LogUtils.i("", "可用的block数目：:"+ availCount+",剩余空间:"+ availCount*blockSize/1024+"KB");
     */
    public static StatFs getSystemStatFs() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long blockCount = sf.getBlockCount();
            long availCount = sf.getAvailableBlocks();

            return sf;
        }
        return null;
    }

    /**
     * 获取内存名称
     */
    public static String getMemoryName() {
        return android.os.Build.PRODUCT;
    }


    /**
     * 获取app版本
     */
    public static String getAppVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }


    /**
     * 退出APP
     */
    public static void exitApp() {
        System.exit(0);
    }

    /**
     * GC
     */
    public static void gc() {
        Runtime.getRuntime().gc();
    }


    /**
     * 检测设备是否支持硬件解码
     */
    public static boolean isHardcode() {
        //读取系统配置文件/system/etc/media_codecc.xml
        File file = new File("/system/etc/media_codecs.xml");
        InputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (Exception e) {
            // TODO: handle exception
        }
        if (in == null) {
            android.util.Log.i("xp", "in == null");
        } else {
            android.util.Log.i("xp", "in != null");
        }


        boolean isHardcode = false;
        XmlPullParserFactory pullFactory;
        try {
            pullFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = pullFactory.newPullParser();
            xmlPullParser.setInput(in, "UTF-8");
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("MediaCodec".equals(tagName)) {
                            String componentName = xmlPullParser.getAttributeValue(0);
                            android.util.Log.i("xp", componentName);
                            if (componentName.startsWith("OMX.")) {
                                if (!componentName.startsWith("OMX.google.")) {
                                    isHardcode = true;
                                }
                            }
                        }
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        android.util.Log.i("xp", "" + isHardcode);
        return isHardcode;
    }

    /**
     * 更新系统时间,需要root
     *
     * @param time
     */
    public static void updateSysTime(long time) {
        LogHelper.i(log, "更新系统时间：" + time);
        try {
            SystemClock.setCurrentTimeMillis(time);
        } catch (SecurityException e) {
            e.printStackTrace();
            LogHelper.i(log, "无法更新系统时间");
        } catch (Exception e) {
            e.printStackTrace();
            LogHelper.i(log, "无法更新系统时间");
        }
    }

    /**
     * 获取cpuabi类型
     */
    public static String[] getCpuAbis() {
        String[] abis;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            abis = Build.SUPPORTED_ABIS;
        } else {
            abis = new String[]{Build.CPU_ABI};
        }
        return abis;
    }

    /**
     * 检测app是否在前台
     */
    public static void checkAppIsForefround(Handler handler, int handlerWhat, Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> runnings = am.getRunningAppProcesses();
                for (ActivityManager.RunningAppProcessInfo running : runnings) {
                    if (running.processName.equals(context.getPackageName())) {
                        Message message = new Message();
                        message.what = handlerWhat;
                        message.obj = false;
                        if (running.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                                || running.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                            message.obj = true;
                        }
                        handler.sendMessage(message);
                        break;
                    }
                }
            }
        }).start();
    }


    /**
     * root截图具体方法
     */
    public static void sreenShot(String filePath) {
        sreenShot(filePath, null);
    }

    /**
     * root截图具体方法
     */
    public static void sreenShot(String filePath, ObservableEmitter<String> observableEmitter) {

        String cmd = "screencap -p " + filePath;
        try {
            //不同的设备权限不一样
            Process process = Runtime.getRuntime().exec("su");
            PrintWriter pw = new PrintWriter(process.getOutputStream());
            pw.println(cmd);
            pw.flush();
            pw.println("exit");
            pw.flush();
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
                if (observableEmitter != null) {
                    observableEmitter.onError(e);
                }
            }
            pw.close();
            process.destroy();
            if (observableEmitter != null) {
                observableEmitter.onNext(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (observableEmitter != null) {
                observableEmitter.onError(e);
            }
        }

        LogHelper.i(log, "截图结束了");
    }


    /**
     * 关闭屏幕 ，其实是使系统休眠
     */
    public static void goToSleep(Activity context) {
//获取设备管理Manager
        DevicePolicyManager policyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
//创建MyAdminReceiver，并获取到该类的ComponentName，
        ComponentName adminReceiver = new ComponentName(context, MyAdminReceiver.class);
//判断设备管理是否已激活
        boolean isActive = policyManager.isAdminActive(adminReceiver);
        if (isActive) {
            LogHelper.i(log, "如果该应用的设备管理权限已激活，则熄灭屏幕");
            //如果该应用的设备管理权限已激活，则熄灭屏幕
            policyManager.lockNow();
        } else {
            LogHelper.i(log, "创建一个添加设备管理的意图");
            //openAdmin();
            //创建一个添加设备管理的意图
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            //激活哪个设备管理器
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminReceiver);
            context.startActivity(intent);
        }

    }

    /**
     * 唤醒屏幕
     *
     * @param context
     */
    public static void wakeUp(Activity context) {
        LogHelper.i(log, "调用唤醒");
        PowerManager pm = ((PowerManager) context.getSystemService(POWER_SERVICE));
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakeLock.acquire();
    }


}
