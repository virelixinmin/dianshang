package com.bw.project_demo.ui.fragment.wode.fragment.mycircle.model;


import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.beans.circlebean;
import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.circleConstance.CircleConstance;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CircleModelImpl implements CircleConstance.CircleModel {
    @Override
    public void responseModel(String sessionId, int userId, final CallBack callBack) {

        HashMap<String, String> map = new HashMap<>();
        map.put("userId",userId+"");
        map.put("sessionId",sessionId);
        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
                .getResponseCircleData(map,1,10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<circlebean>() {
                    @Override
                    public void accept(circlebean circlebean) throws Exception {
                        List<com.bw.project_demo.ui.fragment.wode.fragment.mycircle.beans.circlebean.ResultBean> result = circlebean.getResult();
                        callBack.getData(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
