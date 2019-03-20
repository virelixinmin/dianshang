package com.bw.project_demo.ui.fragment.wode.fragment.address.AddressUpdate.UpdateServiceApp;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;

public interface UpdateServiceApp {
    @PUT("small/user/verify/v1/changeReceiveAddress")
    Observable<ResponseBody> getResponseUpDate(@HeaderMap Map<String,String> map, @QueryMap Map<String,String> map2);

    @POST("small/user/verify/v1/addReceiveAddress")
    Observable<ResponseBody> getResponseAdd(@HeaderMap Map<String,String> map, @QueryMap Map<String,String> map2);
}
