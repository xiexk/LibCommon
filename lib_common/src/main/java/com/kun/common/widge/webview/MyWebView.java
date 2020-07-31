package com.kun.common.widge.webview;

import com.kun.common.tools.LogHelper;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * Created by Administrator on 2016/10/17.
 * 阅读界面WebView
 * 处理点击监听 （如果滑动距离大于8则交给父view处理，反之视为点击事件自己处理）
 * 因为点击事件与点击webview中的图片有冲突 所以延时判断是否点击的内容是图片 是：不响应工具栏 否：改变工具栏
 */
public class MyWebView extends WebView {
  public static String mimType = "text/html; charset=UTF-8";

  public static String encoding = "UTF-8";

  public WebViewJavaScript webViewJavaScript;

  public MyWebViewClient readPageWebViewClient;

  private Context context;

  public MyWebView(Context context) {
    super(context);
    this.context = context;
    init();
  }

  public MyWebView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    init();
  }

  /**
   * 初始化WebView
   */
  public void init() {
    getSettings().setJavaScriptEnabled(true);
    //不缓存
    getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    getSettings().setDefaultTextEncodingName(encoding);
    //打开dom缓存  处理类似Uncaught TypeError: Cannot read property 'lang' of null", source: http://mmc.ud361.com/js/app.0a6b1d37.js的错误
    getSettings().setDomStorageEnabled(true);
    webViewJavaScript = new WebViewJavaScript(context);
    addJavascriptInterface(webViewJavaScript, "toolbox");
    readPageWebViewClient = new MyWebViewClient(this);
    setWebViewClient(readPageWebViewClient);
  }

  /**
   * 加载html文本
   *
   * @param data
   */
  public void loadHtmlData(String data) {
    if (data == null || data.equals("")) {
      LogHelper.e("loadHtmlData", "html数据为空");
    } else {
      //去边框（默认有一定的间距）
      data="<html><head><style>img{width:100% !important;}</style></head><body style='margin:0;padding:0'>"+data+"</body></html>";
      super.loadData(data, mimType, encoding);
    }
  }


  public <T> void setWebViewClient(T webt) {
    setWebViewClient(webt);
  }
}
