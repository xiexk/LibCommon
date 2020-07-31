package com.kun.common.tools;

import java.util.HashSet;
import java.util.List;

/**
 * @author kun
 * Date: 2020/7/22
 * Time: 9:38
 * list工具
 */
public class HelperList {
    /**
     * 判断列表是否为空
     * @return true 为空 false 不为空
     */
    public static boolean isEmpty(List list){
        if(list==null||list.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * list去重
     */
    public static void removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
    }

}
