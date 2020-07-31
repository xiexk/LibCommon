package com.kun.common.data;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kun.common.BaseApplication;
import com.kun.common.data.db.DataBaseHelper;
import com.kun.common.data.sharepreference.SharePreferenceHelper;
import com.kun.common.net.HttpHelper;
import com.kun.common.tools.ObjectStreamHelper;

import android.content.Context;

import okhttp3.OkHttpClient;

/**
 * Created by admin on 2017/3/9.
 * 数据控制器
 */
@Singleton
public class DataManager {


  private HttpHelper httpHelper;

  private SharePreferenceHelper sharePreferenceHelper;

  private DataBaseHelper dataBaseHelper;

  private ObjectStreamHelper objectStreamHelper;

  private Context context;

  public static DataManager getInstence(String objectBathPath, Map<String, String> heads) {

    return new DataManager(BaseApplication.application, new HttpHelper(BaseApplication.application, heads),
        new SharePreferenceHelper(
            BaseApplication.application), new DataBaseHelper(BaseApplication.application), new ObjectStreamHelper());
  }

 public static DataManager getInstence(String objectBathPath, Map<String, String> heads,String baseUrl) {

    return new DataManager(BaseApplication.application, new HttpHelper(BaseApplication.application, heads,baseUrl),
        new SharePreferenceHelper(
            BaseApplication.application), new DataBaseHelper(BaseApplication.application), new ObjectStreamHelper());
  }


  @Inject
  public DataManager(Context context, HttpHelper httpHelper, SharePreferenceHelper sharePreferenceHelper
      , DataBaseHelper dataBaseHelper, ObjectStreamHelper objectStreamHelper) {
    this.context = context;
    this.httpHelper = httpHelper;
    this.sharePreferenceHelper = sharePreferenceHelper;
    this.dataBaseHelper = dataBaseHelper;
    this.objectStreamHelper = objectStreamHelper;
  }

  /**
   * 重新设置http  (用于地址变更)
   */
  public void resetHttpHelper(String url){
    httpHelper.initRetrofitClient(url);
  }

  public <S> S getService(Class<S> serviceClass, OkHttpClient client) {
    return httpHelper.getService(serviceClass,client);
  }

  public <S> S getService(Class<S> serviceClass) {
    return httpHelper.getService(serviceClass);
  }

  public void saveSPData(String key, String value) {
    sharePreferenceHelper.saveData(key, value);
  }

  public void saveSPData(String key, String value, String fileName) {
    sharePreferenceHelper.saveData(key, value, fileName);
  }
  public void saveSPData(String key, int value, String fileName) {
    sharePreferenceHelper.saveData(key, value, fileName);
  }

  public void saveSPMapData(Map<String, String> map) {
    sharePreferenceHelper.saveData(map);
  }

  public void saveSPMapData(Map<String, String> map, String fileName) {
    sharePreferenceHelper.saveData(map, fileName);
  }

  public String getSPData(String key) {
    return sharePreferenceHelper.getValue(key);
  }

  public String getSPData(String key,String fileName) {
    return sharePreferenceHelper.getValue(key,fileName);
  }

  public int getSPDataInt(String key,String fileName) {
    return sharePreferenceHelper.getValueInt(key,fileName);
  } public int getSPDataInt(String key) {
    return sharePreferenceHelper.getValueInt(key);
  } public void saveSPDataInt(String key,int value) {
     sharePreferenceHelper.saveInt(key,value);
  }

  public void deleteSPData() {
    sharePreferenceHelper.deletePreference();
  }

  public void deleteSPData(String fileName) {
    sharePreferenceHelper.deletePreference(fileName);
  }


  public void saveBoolean(String key, boolean value) {
    sharePreferenceHelper.saveBoolean(key, value);
  }

  public boolean getBoolean(String key) {
    return sharePreferenceHelper.getBoolean(key);
  }


  public Map<String, String> getSPMapData() {
    return sharePreferenceHelper.readData();
  }

  public Map<String, String> getSPMapData(String fileName) {
    return sharePreferenceHelper.readData(fileName);
  }


  public <T> void saveObjectData(T object) throws Exception {
    objectStreamHelper.serializeObject(object);
  }

  public <T> T getObjectData(T object) throws Exception {
    return objectStreamHelper.deserializeObject(object);
  }

  public Context getContext() {
    return context;
  }
}
