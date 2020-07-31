package com.kun.common.data.sharepreference;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kun.common.constans.CommonConfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by admin on 2017/3/10.
 * sharePreference
 */
@Singleton
public class SharePreferenceHelper {
  private Context context;

  private static final String TAG = "SharePreferenceHelper";

  @Inject
  public SharePreferenceHelper(Context context) {
    this.context = context;
  }

  private SharedPreferences.Editor getSharePreferenceEditor(String fileName) {
    return getSharedPreferences(fileName).edit();
  }

  private SharedPreferences getSharedPreferences(String fileName) {
    return context
        .getSharedPreferences(TextUtils.isEmpty(fileName) ? CommonConfig.SHARE_PREFERENCE_FILE_NAME : fileName,
            Context.MODE_PRIVATE);
  }

  /*
   * 存数据
   */
  public void saveData(Map<String, String> maps) {
    this.saveData(maps, null);
  }


  public void saveData(Map<String, String> maps, String fileName) {
    if (null == maps || maps.size() == 0) {
      return;
    }
    SharedPreferences.Editor editor = getSharePreferenceEditor(fileName);
    for (Map.Entry<String, String> map : maps.entrySet()) {
      String key = map.getKey();
      String value = map.getValue();
      editor.putString(key, value);
    }
    editor.commit();
  }

  public void saveData(String key, String value) {
    this.saveData(key, value, null);
  }

  public void saveData(String key, String value, String fileName) {
    SharedPreferences.Editor editor = getSharePreferenceEditor(fileName);
    editor.putString(key, value);
    editor.commit();
  }

  public void saveData(String key, int value, String fileName) {
    SharedPreferences.Editor editor = getSharePreferenceEditor(fileName);
    editor.putInt(key, value);
    editor.commit();
  }



  public String getValue(String key) {
    if (null == key) {
      return null;
    }
    return this.getValue(key, null);
  }

  public String getValue(String key, String fileName) {
    if (null == key) {
      return null;
    }
    return getValue(key, fileName, null);
  }

  public String getValue(String key, String fileName, String defaultValue) {
    if (null == key) {
      return null;
    }
    return getSharedPreferences(fileName).getString(key, defaultValue);
  }

  public int getValueInt(String key, String fileName) {
    if (null == key) {
      return 0;
    }
    return getSharedPreferences(fileName).getInt(key, 0);
  }

  public int getValueInt(String key) {
    if (null == key) {
      return 0;
    }
    return getSharedPreferences(null).getInt(key, 0);
  }
  public int getValueInt(String key,int defaultValue) {
    if (null == key) {
      return 0;
    }
    return getSharedPreferences(null).getInt(key, defaultValue);
  }
  public void saveInt(String key, int value) {
    SharedPreferences.Editor editor = getSharePreferenceEditor(null);
    editor.putInt(key, value);
    editor.commit();
  }
  public void saveBoolean(String key, boolean value) {
    SharedPreferences.Editor editor = getSharePreferenceEditor(null);
    editor.putBoolean(key, value);
    editor.commit();
  }

  public boolean getBoolean(String key) {
    if (null == key) {
      return false;
    }
    return getSharedPreferences(null).getBoolean(key, false);
  }
public boolean getBoolean(String key,boolean defaultValue) {
    if (null == key) {
      return false;
    }
    return getSharedPreferences(null).getBoolean(key, defaultValue);
  }


  /*
   * 读数据，返回一个Map<String, String>
   */
  public Map<String, String> readData() {
    return this.readData(null);
  }

  public Map<String, String> readData(String fileName) {
    return (Map<String, String>) getSharedPreferences(fileName).getAll();
  }

  /*
   * 根据文件名删除文件里的数据
   */
  public void deletePreference() {
    this.deletePreference(null);
  }

  public void deletePreference(String fileName) {
    getSharedPreferences(fileName).getAll().clear();
    getSharePreferenceEditor(fileName).clear().commit();
  }
}
