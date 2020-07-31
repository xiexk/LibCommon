package com.kun.common.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数量简化工具
 */
public class CountSimplifyHelper {

    private static int countMoster=1000000;
private static int countCommon=10000;

  public static String simplifyFloat(float f){
    if(f>countMoster){
      return "100w+";
    }
    if(f>countCommon){
      BigDecimal bigDecimal=new BigDecimal(f);
      BigDecimal devi=new BigDecimal(countCommon);
      return bigDecimal.divide(devi,2,RoundingMode.HALF_UP).toString()+"w";
    }
    return f+"";
  }

  public static String simplifyInt(int f){
    if(f>countMoster){
      return "100w+";
    }
    if(f>countCommon){
      BigDecimal bigDecimal=new BigDecimal(f);
      BigDecimal devi=new BigDecimal(countCommon);
      return bigDecimal.divide(devi,2,RoundingMode.HALF_UP).toString()+"w";
    }
    return f+"";
  }

}
