package com.kun.common.data;

import com.kun.common.constans.CommonConfig;

/**
 * Created by Administrator on 2018/12/18.
 * 用户生成默认的
 */

public class BaseDataManagerFactory {
  public static DataManager getLocalDataManager() {
    return DataManager.getInstence(CommonConfig.objectPath, null);
  }
}
