package com.bw.project_demo.ui.fragment.dingdan.fragment.All_orders;

import com.bw.project_demo.ui.fragment.dingdan.fragment.payment.paybean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ServiceApp {
    @GET("small/order/verify/v1/findOrderListByStatus")
    Observable<All_ordersbean> getorders(@HeaderMap Map<String,String> map, @QueryMap Map<String,String> map2);
    @DELETE("small/order/verify/v1/deleteOrder")
    Observable<ResponseBody> getDelete(@HeaderMap Map<String,String> map, @Query("orderId")String id);

    @POST("small/order/verify/v1/pay")
    Observable<paybean> getPayMent(@HeaderMap Map<String,String> map, @Query("orderId") String orderId, @Query("payType") int pay);

    @PUT("small/order/verify/v1/confirmReceipt")
    Observable<ResponseBody> getReceiving(@HeaderMap Map<String,String>map,@Query("orderId")String id);
}
