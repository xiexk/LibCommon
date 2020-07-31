package com.kun.common.domain.searchable;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/1/8.
 * 排序
 */

public class Ordered implements Serializable {
  /**字段名*/
  public String fieldName;

  /**排序方式*/
  public String sortType;
}
