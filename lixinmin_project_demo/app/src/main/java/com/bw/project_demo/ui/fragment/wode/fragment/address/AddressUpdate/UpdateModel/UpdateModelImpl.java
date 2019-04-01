package com.bw.project_demo.ui.fragment.wode.fragment.address.AddressUpdate.UpdateModel;

import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressUpdate.UpdateAddressConstance.UpdateConstance;



import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateModelImpl implements UpdateConstance.UpdateModel {
    @Override
    public void responseAddModel(HashMap<String, String> map2, HashMap<String, String> map, final AddressAddCallBack addressAddCallBack) {
        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
                .getResponseAdd(map2,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String s = responseBody.string().toString();
                        addressAddCallBack.getAddData(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        String message = throwable.getMessage();
                        addressAddCallBack.getAddData(message);
                    }
                });
    }

    @Override
    public void responseModel(HashMap<String, String> map2, HashMap<String, String> map, final AddressCallBack addressCallBack) {
       RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
               .getResponseUpDate(map2,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String s = responseBody.string().toString();
                        addressCallBack.getUpdateData(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        String message = throwable.getMessage();
                        addressCallBack.getUpdateData(message);
                    }
                });
    }
}
