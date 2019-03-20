package com.bw.project_demo.di.model;

import com.bw.project_demo.data.beans.BannerBeans;
import com.bw.project_demo.data.beans.ShopBeans;
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.di.Contract.ContractAll;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class BannerModelImpl implements ContractAll.BannerModel {


    @Override
    public void responseData(final BannerCallBack callBack) {
        OkGo.<String>get(CheckPath.urlBanner).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String banner = response.body().toString();
                Gson g = new Gson();
                BannerBeans bannerBeans = g.fromJson(banner, BannerBeans.class);

                callBack.getData(bannerBeans);
            }
        });
    }

    @Override
    public void ShopresponseData(final ShopCallBack callBack) {
        OkGo.<String>get(CheckPath.urlShop).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String s = response.body().toString();
                Gson g = new Gson();
                ShopBeans shopBeans = g.fromJson(s, ShopBeans.class);
                callBack.ShopgetData(shopBeans);
            }
        });
    }
}
