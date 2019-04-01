package com.bw.project_demo.ui.fragment.search.SearchModel;

import android.app.Service;

import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.ui.fragment.search.SearchBeans;
import com.bw.project_demo.ui.fragment.search.searchContrant.SearchContrants;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchModelImpl implements SearchContrants.SearchModel {
    @Override
    public void responseModel(String name, final SearchCallBack callBack) {
       RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
               .getSearchData(name,1,20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SearchBeans>() {
                    @Override
                    public void accept(SearchBeans searchBeans) throws Exception {
                        callBack.getData(searchBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
