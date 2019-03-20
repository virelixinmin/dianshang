package com.bw.project_demo.ui.fragment.gouwuche.widght;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MyShoppingServiceApp {
    @PUT("small/order/verify/v1/syncShoppingCart")
    Observable<ResponseBody> getShoppingResponseData(@HeaderMap Map<String,String> map,@QueryMap Map<String,String> map2);
//    , @Body("commodityId")int id, @Query("count")int count
    @POST("small/order/verify/v1/createOrder")
    Observable<ResponseBody> getBuyResponseData(@HeaderMap Map<String,String> map,@QueryMap Map<String,String> map2);

}
