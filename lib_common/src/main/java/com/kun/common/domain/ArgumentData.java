package com.kun.common.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Administrator on 2019/1/10.
 * 通用传参数据类
 */
@Data
public class ArgumentData implements Serializable {
  public Long id;

  public String title;

  public String code;

  public String url;

  public String content;

  public int type;

  public Object data;
}
