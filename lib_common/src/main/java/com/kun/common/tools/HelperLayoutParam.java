package com.kun.common.tools;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 布局工具类
 */
public class HelperLayoutParam {

    /**
     * 创建布局参数
     */
    public static RelativeLayout.LayoutParams createParams(int width,int height,int marginLeft,int marginTop,int marginRight,int marginBotttom){
            try {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
                params.setMargins(marginLeft,marginTop, marginRight, marginBotttom);
                return params;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 创建布局参数
     */
    public static FrameLayout.LayoutParams createFramLayoutParams(int width, int height, int marginLeft, int marginTop, int marginRight, int marginBotttom){
            try {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
                params.setMargins(marginLeft,marginTop, marginRight, marginBotttom);
                return params;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 创建布局参数
     */
    public static LinearLayout.LayoutParams createLinearLayoutParams(int width, int height, int marginLeft, int marginTop, int marginRight, int marginBotttom,int weight){
            try {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
                params.setMargins(marginLeft,marginTop, marginRight, marginBotttom);
                params.weight=weight;
                return params;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

}
