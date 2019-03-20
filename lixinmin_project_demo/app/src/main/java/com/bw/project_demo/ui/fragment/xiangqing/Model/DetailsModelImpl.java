package com.bw.project_demo.ui.fragment.xiangqing.Model;

import android.util.Log;

import io.reactivex.Observable;

import com.bw.project_demo.ui.fragment.quanzi.CircleContance.Contance;
import com.bw.project_demo.ui.fragment.xiangqing.DetailsBeans.beans;
import com.bw.project_demo.ui.fragment.xiangqing.DetailsContrant.DetailsContrants;
import com.bw.project_demo.ui.fragment.xiangqing.MyServiceApp;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsModelImpl implements DetailsContrants.DetailsModel {

    @Override
    public void responseModel(int userId, String sessionId, int id, final DetailsCallBack callBack) {

        Retrofit build = new Retrofit.Builder()
                .baseUrl(Contance.detailsString)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HashMap<String, String> map = new HashMap<>();
        map.put("userId",userId+"");
        map.put("sessionId",sessionId);

        MyServiceApp myServiceApp = build.create(MyServiceApp.class);
        Observable<beans> responseData = myServiceApp.getResponseData(map,id);
        responseData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<beans>() {
                    @Override
                    public void accept(beans beans) throws Exception {
                        callBack.getData(beans);
                        Log.d("DetailsModelImpl", "beans:" + beans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
