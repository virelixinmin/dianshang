package com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyModel;

import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyBean.MoneyBean;
import com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyConstance.MoneyStance;


import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoneyModelImpl implements MoneyStance.MoneyModel {
    @Override
    public void ResponseModel(int userId, String sessionId, final MoenyCallBack moenyCallBack) {

        HashMap<String, String> map = new HashMap<>();
        map.put("userId",userId+"");
        map.put("sessionId",sessionId);
        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
                .getResponseMoneyData(map,1,20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoneyBean>() {
                    @Override
                    public void accept(MoneyBean moneyBean) throws Exception {

                        moenyCallBack.getData(moneyBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
