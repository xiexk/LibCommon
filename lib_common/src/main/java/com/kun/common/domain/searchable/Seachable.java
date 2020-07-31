package com.kun.common.domain.searchable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/8.
 * 查询
 */

public class Seachable {
  public DataRangeDto createTime = new DataRangeDto();

  public List<Ordered> ordereds = new ArrayList<>();

  /**关键字*/
  public String key;
}
