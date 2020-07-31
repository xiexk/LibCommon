package com.kun.common.data;

import java.util.Map;

import com.kun.common.net.ResponseTransformer;
import com.kun.common.domain.ResultEntity;
import com.kun.common.net.SchedulerProvider;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.OkHttpClient;

import android.content.Context;

/**
 * DataManager的包装基类,供各module继承
 */

public class BaseDataManager {

  private DataManager mDataManager;


  public BaseDataManager(DataManager mDataManager) {
    this.mDataManager = mDataManager;
  }

  public void saveSPData(String key, String value) {
    mDataManager.saveSPData(key, value);
  }

 public void saveSPData(String key, String value,String fileName) {
    mDataManager.saveSPData(key, value,fileName);
  }
  public void saveSPDataInt(String key, int value,String fileName) {
    mDataManager.saveSPData(key, value,fileName);
  }

  public void saveSPMapData(Map<String, String> map) {
    mDataManager.saveSPMapData(map);
  }

  public void saveSPMapData(Map<String, String> map, String fileName) {
    mDataManager.saveSPMapData(map, fileName);
  }

  public String getSPData(String key) {
    return mDataManager.getSPData(key);
  }
  public String getSPData(String key,String fileName) {
    return mDataManager.getSPData(key,fileName);
  }
  public int getSPDataInt(String key,String fileName) {
    return mDataManager.getSPDataInt(key,fileName);
  }public int getSPDataInt(String key ) {
    return mDataManager.getSPDataInt(key);
  }public void saveSPDataInt(String key,int value ) {
     mDataManager.saveSPDataInt(key,value);
  }

  public void deleteSPData() {
    mDataManager.deleteSPData();
  }

  public void deleteSPData(String fileName) {
    mDataManager.deleteSPData(fileName);
  }

  public Map<String, String> getSPMapData() {
    return mDataManager.getSPMapData();
  }

  public Map<String, String> getSPMapData(String fileName) {
    return mDataManager.getSPMapData(fileName);
  }


  public void saveBoolean(String key, boolean value) {
    mDataManager.saveBoolean(key, value);
  }

  public boolean getBoolean(String key) {
    return mDataManager.getBoolean(key);
  }

  public <T> Disposable defaultResultEntityDeal(Observable<ResultEntity<T>> observable, DisposableObserver consumer) {
    return observable.compose(SchedulerProvider.getInstance().applySchedulers())
        .compose(ResponseTransformer.handleResult())
        .subscribeWith(consumer);
  }

  public <T> Disposable defaultDeal(Observable<T> observable, DisposableObserver consumer) {
    return observable.compose(SchedulerProvider.getInstance().applySchedulers())
        .subscribeWith(consumer);
  }

  public <S> S getService(Class<S> serviceClass) {
    return mDataManager.getService(serviceClass);
  }public <S> S getService(Class<S> serviceClass, OkHttpClient client) {
    return mDataManager.getService(serviceClass,client);
  }

  public Context getContext() {
    return mDataManager.getContext();
  }

  public void resetHttpHelper(String url){
      mDataManager.resetHttpHelper(url);
  }
}
