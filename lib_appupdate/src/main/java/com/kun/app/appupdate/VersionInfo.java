package com.kun.app.appupdate;

import java.io.Serializable;
import java.util.ArrayList;

public class VersionInfo implements Serializable {

  /**
   * 版本号
   */
  public String apkversionId="9999";

  /**
   * 设备id
   */
  public int deviceId;

  /**
   * 版本名称
   */
  public String apkVersion="";

  /**
   * apk文件地址
   */
  public String apkversionUrl;

  @Override
  public String toString() {
    return "VersionInfo{" +
            "apkversionId='" + apkversionId + '\'' +
            ", deviceId=" + deviceId +
            ", apkVersion='" + apkVersion + '\'' +
            ", apkversionUrl='" + apkversionUrl + '\'' +
            '}';
  }
}