package com.bw.project_demo.ui.fragment.wode.fragment.person.ServiceApp;

import com.bw.project_demo.ui.fragment.wode.fragment.person.PersonBean;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface PersonServiceApp {
    @GET("small/user/verify/v1/getUserById")
    Observable<PersonBean> getResponsePersonData(@HeaderMap Map<String,String> map);

    @PUT("small/user/verify/v1/modifyUserNick")
    Observable<ResponseBody> getUpdateNameData(@HeaderMap Map<String,String> map, @Query("nickName")String name);

    @PUT("small/user/verify/v1/modifyUserPwd")
    Observable<ResponseBody> getUpdatePwdData(@HeaderMap Map<String,String> map,@Query("oldPwd")String oldpwd,@Query("newPwd")String newpwd);
    @Multipart
    @POST("small/user/verify/v1/modifyHeadPic")
    Observable<ResponseBody> getImageData(@HeaderMap Map<String,String> map, @Part MultipartBody.Part part);
}
