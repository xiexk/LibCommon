package com.kun.common.net;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kun.common.constans.CommonConfig;
import com.kun.common.net.interceptor.BaseInterceptor;
import com.kun.common.net.support.Retrofit2ConverterFactory;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * http helper负责创建ApiService实例
 * 1、先创建Okhttp 默认的OkHttp包含拦截器、也可以自定义Okhttp
 * 2、创建Retrofit 包含BaseUrl
 * 3、创建ApiService
 */
@Singleton
public class HttpHelper {
    private Context context;

    private Retrofit mRetrofitClient;

    private HashMap<String, Object> mServiceMap;

    private Map<String, String> heads;


    @Inject
    public HttpHelper(Context context, Map<String, String> heads) {
        init(context, heads, null);
    }


    public HttpHelper(Context context, Map<String, String> heads, String baseUrl) {
        init(context, heads, baseUrl);
    }

    private void init(Context context, Map<String, String> heads, String baseUrl) {
        this.context = context;
        this.heads = heads;
        mServiceMap = new HashMap<>();
        initRetrofitClient(baseUrl);
    }

    @SuppressWarnings("unchecked")
    public <S> S getService(Class<S> serviceClass) {
        if (mServiceMap.containsKey(serviceClass.getName())) {
            return (S) mServiceMap.get(serviceClass.getName());
        } else {
            Object obj = createService(serviceClass, null);
            mServiceMap.put(serviceClass.getName(), obj);
            return (S) obj;
        }
    }


    @SuppressWarnings("unchecked")
    public <S> S getService(Class<S> serviceClass, OkHttpClient client) {
        if (mServiceMap.containsKey(serviceClass.getName())) {
            return (S) mServiceMap.get(serviceClass.getName());
        } else {
            Object obj = createService(serviceClass, client);
            mServiceMap.put(serviceClass.getName(), obj);
            return (S) obj;
        }
    }

    public void initRetrofitClient() {
        createRetrofitClient(null);
    }

    public void initRetrofitClient(String baseUrl) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(CommonConfig.HTTP_READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(CommonConfig.HTTP_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new BaseInterceptor<>(heads, context))
                .addInterceptor(new HttpLoggingInterceptor(new HttpLog()).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        mRetrofitClient = createRetrofitClient(httpClient, baseUrl);
    }

    //创建Retrofit
    private Retrofit createRetrofitClient(OkHttpClient httpClient) {
        return createRetrofitClient(httpClient, null);
    }

    //创建Retrofit
    private Retrofit createRetrofitClient(OkHttpClient httpClient, String baseUrl) {
        try {
            return new Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl(baseUrl == null ? CommonConfig.BASE_URL : baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(Retrofit2ConverterFactory.create()).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    //创建Service
    private <S> S createService(Class<S> serviceClass, OkHttpClient client) {
        if (client == null) {
            return mRetrofitClient.create(serviceClass);
        } else {
            return createRetrofitClient(client).create(serviceClass);
        }
    }

}
