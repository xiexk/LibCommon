package com.kun.common.tools;

/**
 * Created by Administrator on 2019/5/15.
 */

public class StringHelper {
  /**
   * 隐藏手机号中间4位
   * @param phone
   * @return
   */
  public static String hidePhoneNumber(String phone){
    String result="";
    try{
      result=phone.substring(0,3)+"****"+phone.substring(7,11);
    }catch (Exception e){
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 特殊字符转义
   */
  public static String string2Json(String s) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < s.length(); i++) {

      char c = s.charAt(i);
      switch (c) {
        case '\"':
          sb.append("\\\"");
          break;
        case '\\':
          sb.append("\\\\");
          break;
        case '/':
          sb.append("\\/");
          break;
        case '\b':
          sb.append("\\b");
          break;
        case '\f':
          sb.append("\\f");
          break;
        case '\n':
          sb.append("\\n");
          break;
        case '\r':
          sb.append("\\r");
          break;
        case '\t':
          sb.append("\\t");
          break;
        default:
          sb.append(c);
      }
  }
    return sb.toString();
  }
}
