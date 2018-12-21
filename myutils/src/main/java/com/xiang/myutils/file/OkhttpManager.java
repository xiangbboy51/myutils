package com.xiang.myutils.file;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/9/8.
 */
public class OkhttpManager {
    //单例模式
    private static OkhttpManager instance;
    private OkHttpClient okHttpClient;
    private Handler handler;
    private Gson gson;

    //进行初始化的方法
    private OkhttpManager() {
        okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(8, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        gson = new Gson();
        handler = new Handler(Looper.getMainLooper());
    }
   //单例模式 只有一个对象
    public static OkhttpManager getInstance() {
        if (instance == null) {
            //同步块
            synchronized (OkhttpManager.class){
            instance = new OkhttpManager();
            }
        }
        return instance;
    }
   //同步回调
    public String get(String url){
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            return okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    //获取回调函数
    public void get(String url, final BaseCallback baseCallback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        //通过okHttpClient 进行获取网络连接的  回调函数
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                baseCallback.onFailure(request, e);
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                String callstr = response.body().string();
                final Object object = gson.fromJson(callstr, baseCallback.getType());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        baseCallback.onSuccess(response,object);
                    }
                });

            }
        });


    }
}
