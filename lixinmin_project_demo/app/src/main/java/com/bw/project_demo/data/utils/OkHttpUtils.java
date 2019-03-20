package com.bw.project_demo.data.utils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpUtils {


    private final OkHttpClient client;
    private static OkHttpUtils utils;
    private OkHttpUtils(){
        client = new OkHttpClient.Builder().build();
    }
    public static  OkHttpUtils getinstance(){
        if (null==utils){
            synchronized (OkHttpUtils.class){
                if (null==utils){
                    utils = new OkHttpUtils();
                }
            }
        }
        return utils;
    }
    public void post(String urlString, FormBody body, Callback callback){
        Request request = new Request.Builder().url(urlString).method("POST",body).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public void get(String urlString,Callback callback){
        Request build = new Request.Builder().url(urlString).build();
        Call call = client.newCall(build);
        call.enqueue(callback);
    }
}
