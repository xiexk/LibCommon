package com.kun.common.net;

import com.kun.common.domain.ResultEntity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2018/9/17.
 * 服务器返回异常处理
 */

public class ResponseTransformer {

  public static <T> ObservableTransformer<ResultEntity<T>, T> handleResult() {
    return upstream -> upstream
        .onErrorResumeNext(new ErrorResumeFunction<>())
        .flatMap(new ResponseFunction<>());
  }


  /**
   * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
   *
   * @param <T>
   */
  private static class ErrorResumeFunction<T> implements
      Function<Throwable, ObservableSource<? extends ResultEntity<T>>> {

    @Override
    public ObservableSource<? extends ResultEntity<T>> apply(Throwable throwable) throws Exception {

      return Observable.error(CustomException.handleException(throwable));
    }
  }


  /**
   * 服务其返回的数据解析
   * 正常服务器返回数据和服务器可能返回的exception
   *
   * @param <T>
   */
  private static class ResponseFunction<T> implements Function<ResultEntity<T>, ObservableSource<T>> {

    @Override
    public ObservableSource<T> apply(ResultEntity<T> tResponse) throws Exception {
      int code = tResponse.getCode();
      String message = tResponse.getMsg();
      if (code == 200) {
        if (tResponse.getData() == null) {
          return Observable.empty();
        }
        return Observable.just(tResponse.getData());
      } else if (code == 503 || code == 504) {
        message = "网络繁忙请稍后重试！";
        return Observable.error(new ApiException(code, message));
      } else {
        return Observable.error(new ApiException(code, message));
      }
    }
  }
}

