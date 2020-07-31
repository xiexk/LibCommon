package com.kun.common.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.kun.common.constans.CommonConfig;

/**
 * Created by Administrator on 2018/12/17.
 * 对象存储
 */

public class ObjectStreamHelper {

  static final String SUFFIX = ".txt";

  /**
   * 序列化保存到sd卡
   */
  public static <T> void serializeObject(T object) {
    serializeObject(object, getDesaultPath());
  }

  /**
   * 序列化保存到sd卡
   */
  public static <T> void serializeObject(T object, String path) {
    try {
      String realPath = path + object.getClass().getName() + SUFFIX;
      File file = SdCardHelper.createFile(realPath);
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
      oos.writeObject(object);
      oos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 反序列化 从sd卡获取
   */
  public static <T> T deserializeObject(T object) {
    return deserializeObject(object, getDesaultPath());
  }

  /**
   * 反序列化 从sd卡获取
   */
  public static <T> T deserializeObject(T object, String path) {
    try {
      String realPath = path + object.getClass().getName() + SUFFIX;
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(realPath)));
      return (T) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      LogHelper.e("反序列化","序列化文件不存在，或读取失败");
    }
    return null;
  }

  /**
   * 获取默认路径
   *
   * @return 默认路径
   */
  private static String getDesaultPath() {
    return CommonConfig.objectPath;
  }
}
