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
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *  请求没网络 从缓存中读取请求
 */
public class QuestInterceptor implements Interceptor {
  private Context context;



  public QuestInterceptor( Context context) {
    this.context = context;
  }


  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    if (!PhoneHelper.isNetworkConnected(context)) {
      request = request.newBuilder()
          .cacheControl(CacheControl.FORCE_CACHE)
          .build();
    }
    return chain.proceed(request);
  }
}
