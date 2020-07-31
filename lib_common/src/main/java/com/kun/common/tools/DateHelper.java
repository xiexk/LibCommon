package com.kun.common.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import android.text.TextUtils;

public class DateHelper {

    // 日期格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YYYY_MM = "yyyy-MM";
    public static final String FORMAT_YYYY = "yyyy";
    public static final String FORMAT_HH_MM = "HH:mm";
    public static final String FORMAT_HH_MM_SS = "HH:mm:ss";
    public static final String FORMAT_MM_SS = "mm:ss";
    public static final String FORMAT_MM_DD_HH_MM = "MM-dd HH:mm";
    public static final String FORMAT_MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";
    public static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YYYY2MM2DD = "yyyy.MM.dd";
    public static final String FORMAT_YYYY2MM2DD_HH_MM = "yyyy.MM.dd HH:mm";
    public static final String FORMAT_MMCDD_HH_MM = "MM月dd日 HH:mm";
    public static final String FORMAT_MMCDD = "MM月dd日";
    public static final String FORMAT_YYYYCMMCDD = "yyyy年MM月dd日";

    private static SimpleDateFormat logSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final long ONE_DAY = 1000 * 60 * 60 * 24;

    //判断选择的日期是否是本周（分从周日开始和从周一开始两种方式）
    public static boolean isThisWeek(Date time) {
//        //周日开始计算
//      Calendar calendar = Calendar.getInstance();

        //周一开始计算
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);

        calendar.setTime(time);

        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);

        if (paramWeek == currentWeek) {
            return true;
        }
        return false;
    }

    //判断选择的日期是否是今天
    public static boolean isToday(Date time) {
        return isThisTime(time, "yyyy-MM-dd");
    }

    //判断选择的日期是否是本月
    public static boolean isThisMonth(Date time) {
        return isThisTime(time, "yyyy-MM");
    }

    //判断选择的日期是否是本月
    public static boolean isThisYear(Date time) {
        return isThisTime(time, "yyyy");
    }

    //判断选择的日期是否是昨天
    public static boolean isYesterDay(Date time) {
        Calendar cal = Calendar.getInstance();
        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        if ((ct - lt) == 1) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isThisTime(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        String param = sdf.format(date);//参数时间

        String now = sdf.format(new Date());//当前时间

        if (param.equals(now)) {
            return true;
        }
        return false;
    }

    /**
     * 获取某年某月有多少天
     */
    public static int getDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0); //输入类型为int类型
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取两个时间相差的天数
     *
     * @return time1 - time2相差的天数
     */
    public static int getDayOffset(long time1, long time2) {
        // 将小的时间置为当天的0点
        long offsetTime;
        if (time1 > time2) {
            offsetTime = time1 - getDayStartTime(getCalendar(time2)).getTimeInMillis();
        } else {
            offsetTime = getDayStartTime(getCalendar(time1)).getTimeInMillis() - time2;
        }
        return (int) (offsetTime / ONE_DAY);
    }

    public static Calendar getCalendar(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
    }

    /**
     * 获取今天时间的开始
     */
    public static Calendar getDayStartTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static String getDurationInString(long time) {
        String durStr = "";
        if (time == 0) {
            return "0秒";
        }
        time = time / 1000;
        long hour = time / (60 * 60);
        time = time - (60 * 60) * hour;
        long min = time / 60;
        time = time - 60 * min;
        long sec = time;
        if (hour != 0) {
            durStr = hour + "时" + min + "分" + sec + "秒";
        } else if (min != 0) {
            durStr = min + "分" + sec + "秒";
        } else {
            durStr = sec + "秒";
        }
        return durStr;
    }

    /**
     * 获取当前时间是星期几
     *
     * @param dt
     * @return
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();

        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    /**
     * 将日期格式的字符串转换为长整型
     *
     * @param date
     * @param format
     * @return
     */
    public static long convertToLong(String date, String format) {
        try {
            if (date != null && (!"".equals(date))) {
                if (TextUtils.isEmpty(format)) {
                    format = TIME_FORMAT;
                }
                SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
                return formatter.parse(date).getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取今天凌晨时间
     */
    public static Date getTodayBeforeDawn(){
        String todayBeforeDawn=getCurrentDatetring();
        return convertStringToDate(todayBeforeDawn,DATE_FORMAT);
    }
    /**
     * 获取今天最后一秒
     */
    public static Date getTodayLastMini(){
        Date d2=getTodayBeforeDawn();
        //一天的毫秒-1秒
        int dayMis=1000*60*60*24;
        long curMillisecond=d2.getTime();
        long resultMis=curMillisecond+(dayMis-1000);
        return new Date(resultMis);
    }

    /**
     * 获取今天的日期
     * @return  "yyyy-MM-dd"
     */
    public static String getCurrentDatetring(){
        return convertToString(new Date(),DATE_FORMAT);
    }

    /**
     * 获取当前的时间
     * @return  "yyyy-MM-dd HH:mm:ss"
     */
    public static String getCurrentTimetring(){
        return convertToString(new Date(),TIME_FORMAT);
    }

    /**
     * 将长整型数字转换为日期格式的字符串
     */
    public static String convertToString(long time) {
        if (time > 0) {
            SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
            Date date = new Date(time);
            return formatter.format(date);
        }
        return "";

    }


    /**
     * 将长整型数字转换为日期格式的字符串
     */
    public static String convertToString(Date date, String format) {
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
            return formatter.format(date);
        }
        return "";
    }

    /**
     * 将字符串转成date
     */
    public static Date convertStringToDate(String dateStr, String format) {
        if (TextUtils.isEmpty(dateStr)) {
            LogHelper.i("解析时间出错","时间字符串不能为空");
            return null;
        }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = null;
            try {
                date = simpleDateFormat.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;

    }

    /**
     * 统一一个地方获取时间,之后服务端时间获取校准方便
     *
     * @return
     */
    public static Date getDate() {
        return new Date();
    }

    public static String getLogDate() {
        return logSDF.format(new Date()) ;
    }

    public static String getLogDate(Date date) {
        return logSDF.format(date);
    }

}
