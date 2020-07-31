package com.kun.app.appupdate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2019/2/13.
 */

public class ThreadUtil {

  /** 线程池 **/
  public static ExecutorService threadPool = Executors.newFixedThreadPool(10);//设置线程最大数目
}
