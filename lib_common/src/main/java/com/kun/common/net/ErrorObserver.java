package com.kun.common.net;

import com.kun.common.mvp.constract.BaseViewContract;

import io.reactivex.observers.DisposableObserver;
import android.util.Log;

/**
 * Created by Administrator on 2018/9/17.
 * 自定义处理错误的Observer
 */

public abstract class ErrorObserver<T> extends DisposableObserver<T> {

  public BaseViewContract baseViewContract;

  public ErrorObserver(BaseViewContract baseViewContract) {
    this.baseViewContract = baseViewContract;
  }

  @Override
  public void onError(Throwable e) {
    String message = "请求失败";
    try {
      ApiException apiException = (ApiException) e;
      message = apiException.getDisplayMessage() + "";
      Log.e("请求失败：", message);
      baseViewContract.showToastLong(message);
    } catch (Exception b) {
      b.printStackTrace();
      if (baseViewContract != null) {
        baseViewContract.showToastLong(message);
      }
    }finally {
      if (baseViewContract != null) {
        baseViewContract.hindWaitingDialog();
      }
    }
  }

  @Override
  public void onComplete() {

  }
}
