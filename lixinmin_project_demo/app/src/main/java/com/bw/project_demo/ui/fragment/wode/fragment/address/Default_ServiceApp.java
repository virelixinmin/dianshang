package com.bw.project_demo.ui.fragment.wode.fragment.address;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Default_ServiceApp {
    @POST("small/user/verify/v1/setDefaultReceiveAddress")
    Observable<ResponseBody> getDefaultData(@HeaderMap Map<String,String> map, @Query("id")int id)  ;
}
