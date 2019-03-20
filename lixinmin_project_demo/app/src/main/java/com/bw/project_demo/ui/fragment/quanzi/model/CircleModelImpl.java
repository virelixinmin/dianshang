package com.bw.project_demo.ui.fragment.quanzi.model;

import com.bw.project_demo.ui.fragment.quanzi.CircleContance.Contance;
import com.bw.project_demo.ui.fragment.quanzi.MyApp.AppService;
import com.bw.project_demo.ui.fragment.quanzi.bean.CircleBeans;
import com.bw.project_demo.ui.fragment.quanzi.contance.CircleData;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CircleModelImpl implements CircleData.CircleModel {
    @Override
    public void ResponseData(int page, final CallBack callBack) {
        Retrofit build = new Retrofit.Builder()
                .baseUrl(Contance.urlString)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AppService appService = build.create(AppService.class);
        Observable<CircleBeans> responseData = appService.getResponseData(page,"20");
        responseData
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CircleBeans>() {
                    @Override
                    public void accept(CircleBeans circleBeans) throws Exception {
                        callBack.getData(circleBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
