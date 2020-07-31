package com.kun.common.tools;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Administrator on 2019/1/9.
 * http解析工具
 */

public class UrlHelper {
  /**
   * 解析http头部
   * @param http
   * @return
   */
  public static String getHost(String http) {
    try {
      return analysisHttp(http).getHost();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 解析http 路径
   * @param http
   * @return
   */
  public static String getPath(String http) {
    try {
      return analysisHttp(http).getPath();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 解析http地址
   * @param http
   * @return
   */
  public static URL analysisHttp(String http) throws MalformedURLException {
    return new URL(http);
  }

  /**
   * 解析url 获取携带参数
   */
  public static UrlEntity parse(String url) {
    UrlEntity entity = new UrlEntity();
    if (url == null) {
      return entity;
    }
    url = url.trim();
    if (url.equals("")) {
      return entity;
    }
    String[] urlParts = url.split("\\?");
    entity.baseUrl = urlParts[0];
    //没有参数
    if (urlParts.length == 1) {
      return entity;
    }
    //有参数
    String[] params = urlParts[1].split("&");
    entity.params = new HashMap<>();
    for (String param : params) {
      String[] keyValue = param.split("=");
      entity.params.put(keyValue[0], keyValue[1]);
    }

    return entity;
  }


  public static class UrlEntity {
    /**
     * 基础url(没有参数)
     */
    public String baseUrl;

    /**
     * url参数
     */
    public Map<String, String> params = new HashMap<>();
  }


  public static String toURLEncoded(String paramString) {
    if (paramString == null || paramString.equals("")) {
      LogHelper.i("toURLEncoded error:",paramString);
      return "";
    }

    try
    {
      String str = new String(paramString.getBytes(), "UTF-8");
      str = URLEncoder.encode(str, "UTF-8");
      return str;
    }
    catch (Exception localException)
    {
      LogHelper.e("toURLEncoded error:",paramString, localException);
    }

    return "";
  }


  /**
   * 给url添加参数
   */
  public static String addUrlParams(String url,HashMap<String,String> params){
    String res=url+"?";
    Iterator<Entry<String,String>> iterator=params.entrySet().iterator();
    while (iterator.hasNext()){
      Map.Entry<String,String> m=iterator.next();
      String key=m.getKey();
      String value=m.getValue();
      res=res+key+"="+value+"&";
    }
    if(res.substring(res.length()-1).equals("&")){
      res=res.substring(0,res.length()-1);
    }
    return res;
  }

  /**
   * url解码

   */
  public static String urlDecoded(String paramString) {
    if (paramString == null || paramString.equals("")) {
      return "";
    }

    try
    {
      String str = new String(paramString.getBytes(), "UTF-8");
      str = URLDecoder.decode(str, "UTF-8");
      return str;
    }
    catch (Exception localException)
    {
      LogHelper.e("url解码","toURLDecoded error:"+paramString, localException);
    }

    return "";
  }




}
