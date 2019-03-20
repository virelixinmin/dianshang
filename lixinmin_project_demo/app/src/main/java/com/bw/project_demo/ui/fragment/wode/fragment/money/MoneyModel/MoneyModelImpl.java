package com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyModel;

import com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyBean.MoneyBean;
import com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyConstance.MoneyStance;
import com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyServiceApp.MoneyServiceApp;
import com.bw.project_demo.ui.fragment.wode.fragment.money.constance;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoneyModelImpl implements MoneyStance.MoneyModel {
    @Override
    public void ResponseModel(int userId, String sessionId, final MoenyCallBack moenyCallBack) {
        Retrofit build = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(constance.urlMoneyString)
                .build();
        MoneyServiceApp moneyServiceApp = build.create(MoneyServiceApp.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("userId",userId+"");
        map.put("sessionId",sessionId);
        Observable<MoneyBean> responseMoneyData = moneyServiceApp.getResponseMoneyData(map, 1, 1);
        responseMoneyData.subscribeOn(Schedulers.io())
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
