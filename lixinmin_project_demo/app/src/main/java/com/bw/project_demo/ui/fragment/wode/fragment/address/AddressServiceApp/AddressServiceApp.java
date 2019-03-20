package com.bw.project_demo.ui.fragment.wode.fragment.address.AddressServiceApp;

import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressBeans.AddressBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

public interface AddressServiceApp {
    @GET("small/user/verify/v1/receiveAddressList")
    Observable<AddressBean> getAddressResponseData(@HeaderMap Map<String,String> map);

}
