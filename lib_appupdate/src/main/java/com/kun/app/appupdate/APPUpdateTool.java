package com.kun.app.appupdate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.alibaba.fastjson.JSON;

/**
 *
 */
public class APPUpdateTool {

  /**
   * 获取版本更新信息
   *
   * @param is 输入流
   * @return 版本信息
   */
  public static VersionInfo getUpdateInfo(InputStream is) {

    VersionInfo info = null;
    StringBuffer stringBuffer = new StringBuffer();
    try {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
      String read = "";
      while ((read = bufferedReader.readLine()) != null) {
        stringBuffer.append(read);
      }
      bufferedReader.close();
      info = JSON.parseObject(stringBuffer.toString(), VersionInfo.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return info;
  }
}