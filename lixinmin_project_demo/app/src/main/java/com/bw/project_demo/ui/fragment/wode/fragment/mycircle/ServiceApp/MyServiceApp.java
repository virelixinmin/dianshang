package com.bw.project_demo.ui.fragment.wode.fragment.mycircle.ServiceApp;

import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.beans.circlebean;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MyServiceApp {
    @GET("small/circle/verify/v1/findMyCircleById")
    Observable<circlebean> getResponseCircleData(@HeaderMap Map<String,String> map, @Query("page")int page,@Query("count")int count);

    @Multipart
    @POST("small/circle/verify/v1/releaseCircle")
    Observable<ResponseBody> getResponseData(@HeaderMap Map<String,String> map, @Part List<MultipartBody.Part> list);

    @DELETE("small/circle/verify/v1/deleteCircle")
    Observable<ResponseBody> getResponseBodydele(@HeaderMap Map<String,String> map,@Query("circleId")int circleId);
}
