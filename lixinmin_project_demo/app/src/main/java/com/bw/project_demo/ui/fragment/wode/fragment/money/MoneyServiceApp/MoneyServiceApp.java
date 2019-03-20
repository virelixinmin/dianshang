package com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyServiceApp;

import com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyBean.MoneyBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;

public interface MoneyServiceApp {
    @GET("small/user/verify/v1/findUserWallet")
    Observable<MoneyBean> getResponseMoneyData(@HeaderMap Map<String,String> map, @Query("page")int page, @Query("count")int count);
}
