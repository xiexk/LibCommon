package com.kun.common.data.db;

import javax.inject.Inject;
import javax.inject.Singleton;

import android.content.Context;

/**
 * Created by admin on 2017/3/10.
 */
@Singleton
public class DataBaseHelper {
  private Context context;

  @Inject
  public DataBaseHelper(Context context) {
    this.context = context;
  }
}
