package com.kun.common.tools;

import android.os.Bundle;

/**
 * Created by Administrator on 2019/5/20.
 */

public class BundleHelper {

  public static  void printBundle(Bundle bundle) {
    if (bundle == null) {
      return ;
    }
    String string = "Bundle{";
    for (String key : bundle.keySet()) {
      string += " " + key + " => " + bundle.get(key) + ";";
    }
    string += " }Bundle";
    System.out.println(string);
  }
}
