package com.kun.common.tools;

import android.graphics.Color;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.kun.common.BaseApplication;

import java.util.regex.Pattern;

/**
 * 颜色转换工具
 */
public class HelperColor {

    /**
     * 获取颜色值 #开头或者rgb(152,245,255)
     */
    public static Integer getColorForStr(String strColor) {
        Integer color = Color.BLACK;
        try {
            if (strColor == null || "".equals(strColor)) {
                return null;
            }
            //说明是#000000类型
            if (strColor.contains("#")) {
                color = Color.parseColor(strColor);
            } else if(strColor.contains("rgb")||strColor.contains("RGB")||strColor.contains("rgba")||strColor.contains("RGBA")){

                String[] colorArr = strColor.substring(strColor.indexOf("(") + 1, strColor.indexOf(")")).split(",");
                if (colorArr.length > 3) {
                    int alph=((Double)(Double.parseDouble(colorArr[3].trim()) * 255)).intValue();
                    color = Color.argb(alph, Integer.parseInt(colorArr[0].trim()), Integer.parseInt(colorArr[1].trim()), Integer.parseInt(colorArr[2].trim()));
                } else {
                    color = Color.rgb(Integer.parseInt(colorArr[0].trim()), Integer.parseInt(colorArr[1].trim()), Integer.parseInt(colorArr[2].trim()));
                }
            }else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return color;
    }

    /**
     * 获取资源中的颜色
     */
    public static int getResourcesColor(int colorId) {

        int ret = 0x00ffffff;
        try {
            ret = BaseApplication.application.getResources().getColor(colorId);
        } catch (Exception e) {
        }
        return ret;
    }

    /**
     * 将十六进制 颜色代码 转换为 int
     */
    public static int hextoColor(String color) {
        // #ff00CCFF
        String reg = "#[a-f0-9A-F]{8}";
        if (!Pattern.matches(reg, color)) {
            color = "#00ffffff";
        }
        return Color.parseColor(color);
    }

    /**
     * 修改颜色透明度
     */
    public static int changeAlpha(int color, int alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * 修改颜色透明度
     */
    public static String getRgbStr(int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return "rgb("+red+","+green+","+blue+")";
    }

    /**
     * 添加透明度
     * alpha 0-100 透明-不透明
     */
    public static int addRgbAlpha(String rgb,int alpha){
        //转换成 0-1
        double a=alpha;
       double alphaDouble=a/100;
        return addRgbAlpha(rgb,alphaDouble);
    }
    /**
     * 添加透明度
     * alpha 0-1 透明-不透明 0.5半透明
     */
    public static int addRgbAlpha(String rgb,double alpha){
        String rgbnew=rgb.replace(")",","+alpha+")");
        Log.i("添加透明度:","原始颜色："+rgb+" 透明度："+alpha+" 组合后颜色："+rgbnew);
        return getColorForStr(rgbnew);
    }

}
