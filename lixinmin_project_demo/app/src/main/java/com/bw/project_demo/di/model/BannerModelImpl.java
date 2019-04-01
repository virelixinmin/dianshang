package com.bw.project_demo.di.model;

import com.bw.project_demo.data.beans.BannerBeans;
import com.bw.project_demo.data.beans.ShopBeans;
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.di.Contract.ContractAll;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BannerModelImpl implements ContractAll.BannerModel {


    @Override
    public void responseData(final BannerCallBack callBack) {
        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
                .getBannersData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BannerBeans>() {
                    @Override
                    public void accept(BannerBeans bannerBeans) throws Exception {
                        callBack.getData(bannerBeans);
                    }
                });
    }

    @Override
    public void ShopresponseData(final ShopCallBack callBack) {
        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
                .getShopData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ShopBeans>() {
                    @Override
                    public void accept(ShopBeans shopBeans) throws Exception {
                        callBack.ShopgetData(shopBeans);
                    }
                });
    }
}
