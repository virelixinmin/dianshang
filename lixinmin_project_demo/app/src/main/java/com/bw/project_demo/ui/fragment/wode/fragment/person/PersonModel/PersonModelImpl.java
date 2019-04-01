package com.bw.project_demo.ui.fragment.wode.fragment.person.PersonModel;

import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.ui.fragment.wode.fragment.person.PersonBean;
import com.bw.project_demo.ui.fragment.wode.fragment.person.PersonConstance.PersonConstance;


import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonModelImpl implements PersonConstance.PersonModel {
    @Override
    public void PersonresponseModel(int userId, String sessionId, final PersonCallBack personCallBack) {

        HashMap<String, String> map = new HashMap<>();
        map.put("userId",userId+"");
        map.put("sessionId",sessionId);
        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
                .getResponsePersonData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PersonBean>() {
                    @Override
                    public void accept(PersonBean personBean) throws Exception {
                        personCallBack.getPersonData(personBean);
                    }
                });
    }
}
