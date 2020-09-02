package com.r.http.cn.httpUrl;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/3/27.
 */

public class RetrofitBean {
    private static POSTService postService;
    private static final String API_HOST = "http://api.u-launcher.com";
    private static final int DEFAULT_TIMEOUT = 6;
    public static POSTService getApi(String ip) {
        postService = null;
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(5, TimeUnit.SECONDS)
                .pingInterval(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS);
        //初始化retrofit框架
        Retrofit build = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                //1.配置主机地址
                .baseUrl(HttpUrl.BASE_URL+ip+":8080/")
                //2.解析json的工具
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();

        //读取接口上面的参数
        postService = build.create(POSTService.class);
        return postService;
    }
    public static POSTService getApiApi() {
        postService = null;
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        //初始化retrofit框架
        Retrofit build = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                //1.配置主机地址
                .baseUrl(HttpUrl.BASE_URLAPI)
                //2.解析json的工具
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();

        //读取接口上面的参数
        postService = build.create(POSTService.class);

        return postService;
    }
    public static POSTService getApi2() {
        postService = null;
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        //初始化retrofit框架
        Retrofit build = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                //1.配置主机地址
                .baseUrl(HttpUrl.BASE_URL2)
                //2.解析json的工具
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();

        //读取接口上面的参数
        postService = build.create(POSTService.class);

        return postService;
    }
    public static POSTService getFx() {
        postService = null;
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        //初始化retrofit框架
        Retrofit build = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                //1.配置主机地址
                .baseUrl(HttpUrl.SHARED)
                //2.解析json的工具
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();

        //读取接口上面的参数
        postService = build.create(POSTService.class);

        return postService;
    }

    public static POSTService getApi3() {
        //初始化retrofit框架
        postService = null;
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        Retrofit build = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                //1.配置主机地址
                .baseUrl("http://www.rujiaowang.net/")
                //2.解析json的工具
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();

        //读取接口上面的参数
        postService = build.create(POSTService.class);
        return postService;
    }

    public static POSTService getApi4(String ip) {
        postService = null;
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        //初始化retrofit框架
        Retrofit build = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                //1.配置主机地址
                .baseUrl("http://"+ip+":62003/")
                //2.解析json的工具
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();

        //读取接口上面的参数
        postService = build.create(POSTService.class);

        return postService;
    }

    public static POSTService getDownloadApi() {
        postService = null;
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        Retrofit build = new Retrofit.Builder()
                .baseUrl(API_HOST)
                .client(client)
                .build();
        postService  = build.create(POSTService.class);
        return  postService;
    }
    public static POSTService getApi5(String ip) {
        postService = null;
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        //初始化retrofit框架
        Retrofit build = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                //1.配置主机地址
                .baseUrl("http://"+ip+":8080/")
                //2.解析json的工具
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();

        //读取接口上面的参数
        postService = build.create(POSTService.class);

        return postService;
    }
}
