package com.kun.common.widge.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.kun.app.popwin.PopMainActivity;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPreview;

/**
 * Created by Administrator on 2016/8/17.
 * webview 脚本点击事件
 */
public class WebViewJavaScript {
  private Context context;

  public static boolean clickimage = false;

  public WebViewJavaScript(Context context) {
    this.context = context;
  }

  /**
   * 图片点击
   */
  @JavascriptInterface
  public void ImageClick(int i, String src) {
    clickimage = true;

    Log.v("WebViewJavaScript", "ImageClick");
    ArrayList<String> urls = new ArrayList<>();
    urls.add(src);
    PhotoPreview.builder()
        .setPhotos(urls)
        .setShowDeleteButton(false)
        .start((Activity) context);
  }

  /**
   * 设备注册成功后跳转到主页面
   */
  @JavascriptInterface
  public void SuccessClick() {
//    Intent it = new Intent();
//    ComponentName componentName = new ComponentName("com.tc.smlite.main.activity", "com.tc.smlite.main.activity.PopMainActivity");
//    it.setComponent(componentName);
//    context.startActivity(it);

    Intent intent = new Intent();
    intent.setClass(context, PopMainActivity.class);
    context.startActivity(intent);
  }

  @JavascriptInterface
  public void bodyClick() {
    Log.v("WebViewJavaScript", "bodyClick");
    try {
      //  ((ReadPageNew) context).handler.sendEmptyMessage(-9);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
