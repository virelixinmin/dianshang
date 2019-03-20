package com.bw.project_demo.ui.fragment.xiangqing;



import com.bw.project_demo.data.beans.Taobao;
import com.bw.project_demo.ui.fragment.xiangqing.DetailsBeans.beans;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;

public interface MyServiceApp {
    @GET("small/commodity/v1/findCommodityDetailsById")
    io.reactivex.Observable<beans> getResponseData(@HeaderMap Map<String,String>map,@Query("commodityId")int id);
    @GET("getGoodsLink?appkey=c45726699b930ef75828ad53830d03fd&page=1&type=so")
    Observable<Taobao> getReponseTaobao(@Query("keyword")String name);
}
