package com.kun.common.domain;

/**
 * 请求返回父类
 * Created by Administrator on 2018/9/17.
 */

public class ResultEntity<T> {
  /**
   * 返回的code
   */
  private int code;
  /**
   *  具体的数据结果
   */
  private T data;
  /**
   * msg 可用来返回接口的说明
   */
  private String msg;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}

