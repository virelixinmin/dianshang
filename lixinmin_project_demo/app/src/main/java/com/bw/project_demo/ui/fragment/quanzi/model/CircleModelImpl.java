package com.bw.project_demo.ui.fragment.quanzi.model;

import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;

import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.ui.fragment.quanzi.bean.CircleBeans;
import com.bw.project_demo.ui.fragment.quanzi.contance.CircleData;

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
        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class).getResponseData(page,"20")
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
