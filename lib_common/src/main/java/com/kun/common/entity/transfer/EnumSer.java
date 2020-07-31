package com.kun.common.entity.transfer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.kun.common.entity.BaseEnum;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 枚举类自动转换
 *
 * @param <E>
 */
public class EnumSer<E extends BaseEnum> implements ObjectSerializer, ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Class<Enum> enumClass = (Class<Enum>) type;
        E[] objects = (E[]) enumClass.getEnumConstants();
        Object valus = parser.parse();
        if (valus == null) {
            return null;
        }
        int enumType = (int) valus;
        return (T) objects[0].valueOf(enumType);
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        serializer.write(((E) (object)).getType());
    }
}
