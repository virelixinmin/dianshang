package com.bw.project_demo.di.model;

import android.util.Log;

import com.bw.project_demo.data.beans.FindShoppingBean;
import com.bw.project_demo.data.beans.ShoppingBeans;
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.di.Contract.ShoppingContract;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class    ShoppingModelImpl implements ShoppingContract.ShoppingModel {
    private int id = 1002;
    @Override
    public void responseModel(int userId, String sessionId, final CallBack callBack) {

        OkGo.<String>get(CheckPath.urlShopping)
                .headers("userId",userId+"")
                .headers("sessionId",sessionId)
                .execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String s = response.body().toString();
                Gson g = new Gson();
                FindShoppingBean shoppingBeans = g.fromJson(s, FindShoppingBean.class);
                Log.d("ShoppingModelImpl", "shoppingBeans:" + shoppingBeans.getResult());
                callBack.getData(shoppingBeans);
            }
        });
    }
}
