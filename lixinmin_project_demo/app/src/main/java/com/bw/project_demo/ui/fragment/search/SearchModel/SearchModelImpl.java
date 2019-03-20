package com.bw.project_demo.ui.fragment.search.SearchModel;

import com.bw.project_demo.ui.fragment.search.SearchBeans;
import com.bw.project_demo.ui.fragment.search.searchContrant.SearchContrants;
import com.bw.project_demo.ui.fragment.search.widght.MySearchServiceApp;
import com.bw.project_demo.ui.fragment.search.widght.SearchCotant;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchModelImpl implements SearchContrants.SearchModel {
    @Override
    public void responseModel(String name, final SearchCallBack callBack) {
        Retrofit build = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SearchCotant.SearchUrl)
                .build();
        MySearchServiceApp mySearchServiceApp = build.create(MySearchServiceApp.class);
        Observable<SearchBeans> searchData = mySearchServiceApp.getSearchData(name,1,20);
        searchData.subscribeOn(Schedulers.io())
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
