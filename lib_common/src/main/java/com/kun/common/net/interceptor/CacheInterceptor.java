/*
 *    Copyright (C) 2016 Tamic
 *
 *    link :https://github.com/Tamicer/Novate
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.kun.common.net.interceptor;

import java.io.IOException;

import com.kun.common.tools.PhoneHelper;

import android.content.Context;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *  response缓存
 */
public class CacheInterceptor implements Interceptor {
  private Context context;



  public CacheInterceptor( Context context) {
    this.context = context;
  }


  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    Response response = chain.proceed(request);
    if (PhoneHelper.isNetworkConnected(context)) {
      int maxAge = 0 * 60;
      // 有网络时 设置缓存超时时间0个小时
      response.newBuilder()
          .header("Cache-Control", "public, max-age=" + maxAge)
          .removeHeader("Pragma")
          .build();
    } else {
      // 无网络时，设置超时为1周
      int maxStale = 60 * 60 * 24 * 7;
      response.newBuilder()
          .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
          .removeHeader("Pragma")
          .build();
    }
    return response;
  }
}
