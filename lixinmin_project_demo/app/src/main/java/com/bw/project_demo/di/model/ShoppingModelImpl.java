package com.bw.project_demo.di.model;

import android.util.Log;

import com.bw.project_demo.data.beans.FindShoppingBean;
import com.bw.project_demo.data.beans.ShoppingBeans;
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.di.Contract.ShoppingContract;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class  ShoppingModelImpl implements ShoppingContract.ShoppingModel {
    private int id = 1002;
    @Override
    public void responseModel(int userId, String sessionId, final CallBack callBack) {

        HashMap<String, String> map = new HashMap<>();
        map.put("userId",userId+"");
        map.put("sessionId",sessionId);
        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
                .getShoppingBean(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FindShoppingBean>() {
                    @Override
                    public void accept(FindShoppingBean findShoppingBean) throws Exception {
                        callBack.getData(findShoppingBean);
                    }
                });

    }
}
