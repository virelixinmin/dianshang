package com.bw.project_demo.ui.fragment.quanzi.MyApp;

;

import com.bw.project_demo.ui.fragment.quanzi.bean.CircleBeans;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppService {
    @GET("small/circle/v1/findCircleList")
    Observable<CircleBeans> getResponseData(@Query("page") int page, @Query("count")String count);

    }


