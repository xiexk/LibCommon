package com.kun.common.entity;

/**
 * 枚举基础类
 * @param <T>
 */
public interface BaseEnum<T extends Enum>{
    /**
     * 获取类型（自定义类型 int）
     */
    Object getType();

    /**
     * 根据type值获取枚举类
     */
    T valueOf(int type);
}
