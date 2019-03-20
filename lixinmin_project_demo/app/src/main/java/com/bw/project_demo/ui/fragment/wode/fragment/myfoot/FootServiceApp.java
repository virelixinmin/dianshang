package com.bw.project_demo.ui.fragment.wode.fragment.myfoot;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;

public interface FootServiceApp {
    @GET("small/commodity/verify/v1/browseList")
    Observable<FootBean> getFootResponseData(@HeaderMap Map<String,String> map, @Query("page")int page,@Query("count")int count);
}
