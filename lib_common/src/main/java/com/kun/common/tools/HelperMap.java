package com.kun.common.tools;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;


public class HelperMap {

    /**
     * map转String数组
     */
    public static String[] getMapToStringArray(Map<String, String> map) {
        Set<String> keySet = map.keySet();
        //将set集合转换为数组
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        String[] vauleArray=new String[keySet.size()];
        //给数组排序(升序)
        Arrays.sort(keyArray);
        for (int i = 0; i < keyArray.length; i++) {
            vauleArray[i]=map.get(keyArray[i]);
        }
        return vauleArray;
    }

}
