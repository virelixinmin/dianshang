package com.bw.project_demo.ui.fragment.wode.fragment.address.AddressModel;

import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressBeans.AddressBean;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressConstance.AddressContance;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressServiceApp.AddressServiceApp;
import com.bw.project_demo.ui.fragment.wode.fragment.address.Constance.constan;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddressModelmpl implements AddressContance.AddressModel {

    private AddressServiceApp addressServiceApp;

    @Override
    public void responseModel(int userId, String sessionId, final CallBack callBack) {
        Retrofit build = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(constan.urlString)
                .build();
        addressServiceApp = build.create(AddressServiceApp.class);
        HashMap<String,String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("userId",userId+"");
        objectObjectHashMap.put("sessionId",sessionId);
        Observable<AddressBean> addressResponseData = addressServiceApp.getAddressResponseData(objectObjectHashMap);
        addressResponseData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AddressBean>() {
                    @Override
                    public void accept(AddressBean addressBean) throws Exception {
                        List<AddressBean.ResultBean> result = addressBean.getResult();
                        callBack.getData(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
