package com.bw.project_demo.ui.fragment.wode.fragment.person.PersonModel;

import com.bw.project_demo.ui.fragment.wode.fragment.person.PersonBean;
import com.bw.project_demo.ui.fragment.wode.fragment.person.PersonConstance.PersonConstance;
import com.bw.project_demo.ui.fragment.wode.fragment.person.ServiceApp.PersonServiceApp;
import com.bw.project_demo.ui.fragment.wode.fragment.person.constanccc;

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
        Retrofit build = new Retrofit.Builder().baseUrl(constanccc.urlPersonString)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        PersonServiceApp personServiceApp = build.create(PersonServiceApp.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("userId",userId+"");
        map.put("sessionId",sessionId);
        Observable<PersonBean> responsePersonData = personServiceApp.getResponsePersonData(map);
        responsePersonData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PersonBean>() {
                    @Override
                    public void accept(PersonBean personBean) throws Exception {
                        personCallBack.getPersonData(personBean);
                    }
                });
    }
}
