package com.kun.common.domain.searchable;

import java.util.Collection;

/**
 * Created by Administrator on 2019/1/8.
 */

public class ListWrapper<T> {
  private long total;
  private long size;
  private Collection<T> list;

  public ListWrapper() {
  }

  public Collection<T> getList() {
    return this.list;
  }

  public void setList(Collection<T> list) {
    this.list = list;
  }


  public String toString() {
    return "ListWrapper(list=" + this.getList() + ")";
  }

  public ListWrapper(Collection<T> list) {
    this.list = list;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }
}
