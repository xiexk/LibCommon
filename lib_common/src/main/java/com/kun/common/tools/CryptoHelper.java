package com.kun.common.tools;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Base64;

/**
 * Created by Administrator on 2019/1/2.
 * des 加解密
 */

public class CryptoHelper {

  /**
   * 加密
   * @param source  原字符
   * @return 加密后的字符串
   */
  public static String encodeBase64(String source) {
    return Base64.encodeToString(source.getBytes(), Base64.DEFAULT);
  }

  /**
   * 解密
   * @param source  原字符
   * @return 解密后的字符串
   */
  public static String decodeBase64(String source) {
    return new String(Base64.decode(source.getBytes(), Base64.DEFAULT));
  }

  /**
   * MD5 加密
   * @param source 原字符
   */
  public static String MD5(String source) {

    String result = "";
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(source.getBytes());
      byte b[] = md.digest();
      int i;
      StringBuffer buf = new StringBuffer("");
      for (int offset = 0; offset < b.length; offset++) {
        i = b[offset];
        if (i < 0) {
          i += 256;
        }
        if (i < 16) {
          buf.append("0");
        }
        buf.append(Integer.toHexString(i));
      }
      result = buf.toString();
    } catch (NoSuchAlgorithmException e) {
      LogHelper.e(e);
    }
    return result;
  }
}
