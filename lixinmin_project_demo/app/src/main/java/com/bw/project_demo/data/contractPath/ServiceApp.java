package com.bw.project_demo.data.contractPath;

import com.bw.project_demo.data.beans.BannerBeans;
import com.bw.project_demo.data.beans.FindShoppingBean;
import com.bw.project_demo.data.beans.ShopBeans;
import com.bw.project_demo.data.beans.Taobao;
import com.bw.project_demo.ui.fragment.dingdan.fragment.All_orders.All_ordersbean;
import com.bw.project_demo.ui.fragment.dingdan.fragment.payment.paybean;
import com.bw.project_demo.ui.fragment.quanzi.bean.CircleBeans;
import com.bw.project_demo.ui.fragment.search.SearchBeans;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressBeans.AddressBean;
import com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyBean.MoneyBean;
import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.beans.circlebean;
import com.bw.project_demo.ui.fragment.wode.fragment.myfoot.FootBean;
import com.bw.project_demo.ui.fragment.wode.fragment.person.PersonBean;
import com.bw.project_demo.ui.fragment.xiangqing.DetailsBeans.beans;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ServiceApp {

    //注册
    @POST("small/user/v1/register")
    Observable<ResponseBody> getRegister(@Query("phone") String phone,@Query("pwd") String pwd);

    //登录
    @POST("small/user/v1/login")
    Observable<ResponseBody> getLogin(@Query("phone") String phone,@Query("pwd") String pwd);

    @GET("small/order/verify/v1/findShoppingCart")
    Observable<FindShoppingBean> getShoppingBean(@HeaderMap Map<String,String> map);

    //查询商品接口
    @GET("small/commodity/v1/findCommodityByKeyword")
    Observable<SearchBeans> getSearchData(@Query("keyword")String name, @Query("page")int page, @Query("count")int count);

    //首页商品热销精品接口
    @GET("small/commodity/v1/commodityList")
    Observable<ShopBeans> getShopData();

    //Banner列表
    @GET("small/commodity/v1/bannerShow")
    Observable<BannerBeans> getBannersData();

    //同步购物车的接口
    @PUT("small/order/verify/v1/syncShoppingCart")
    Observable<ResponseBody> getShoppingResponseData(@HeaderMap Map<String,String> map,@QueryMap Map<String,String> map2);

    //创建订单的接口
    @POST("small/order/verify/v1/createOrder")
    Observable<ResponseBody> getBuyResponseData(@HeaderMap Map<String,String> map,@QueryMap Map<String,String> map2);

    //查询所有的订单接口
    @GET("small/order/verify/v1/findOrderListByStatus")
    Observable<All_ordersbean> getorders(@HeaderMap Map<String,String> map, @QueryMap Map<String,String> map2);

    //删除订单的接口
    @DELETE("small/order/verify/v1/deleteOrder")
    Observable<ResponseBody> getDelete(@HeaderMap Map<String,String> map, @Query("orderId")String id);

    //支付的接口
    @POST("small/order/verify/v1/pay")
    Observable<paybean> getPayMent(@HeaderMap Map<String,String> map, @Query("orderId") String orderId, @Query("payType") int pay);

    //确认收货的接口
    @PUT("small/order/verify/v1/confirmReceipt")
    Observable<ResponseBody> getReceiving(@HeaderMap Map<String,String>map,@Query("orderId")String id);

    //查询圈子接口
    @GET("small/circle/v1/findCircleList")
    Observable<CircleBeans> getResponseData(@Query("page") int page, @Query("count")String count);

    //收货地址列表
    @GET("small/user/verify/v1/receiveAddressList")
    Observable<AddressBean> getAddressResponseData(@HeaderMap Map<String,String> map);

    //修改收货地址接口
    @PUT("small/user/verify/v1/changeReceiveAddress")
    Observable<ResponseBody> getResponseUpDate(@HeaderMap Map<String,String> map, @QueryMap Map<String,String> map2);

    //添加收货地址接口
    @POST("small/user/verify/v1/addReceiveAddress")
    Observable<ResponseBody> getResponseAdd(@HeaderMap Map<String,String> map, @QueryMap Map<String,String> map2);

    //设置默认收货地址
    @POST("small/user/verify/v1/setDefaultReceiveAddress")
    Observable<ResponseBody> getDefaultData(@HeaderMap Map<String,String> map, @Query("id")int id);

    //查询用户钱包接口
    @GET("small/user/verify/v1/findUserWallet")
    Observable<MoneyBean> getResponseMoneyData(@HeaderMap Map<String,String> map, @Query("page")int page, @Query("count")int count);

    //查询我的圈子接口
    @GET("small/circle/verify/v1/findMyCircleById")
    Observable<circlebean> getResponseCircleData(@HeaderMap Map<String,String> map, @Query("page")int page, @Query("count")int count);

    //发布圈子接口
    @Multipart
    @POST("small/circle/verify/v1/releaseCircle")
    Observable<ResponseBody> getResponseData(@HeaderMap Map<String,String> map, @Part List<MultipartBody.Part> list);

    //删除圈子接口
    @DELETE("small/circle/verify/v1/deleteCircle")
    Observable<ResponseBody> getResponseBodydele(@HeaderMap Map<String,String> map,@Query("circleId")int circleId);

    //我的足迹接口
    @GET("small/commodity/verify/v1/browseList")
    Observable<FootBean> getFootResponseData(@HeaderMap Map<String,String> map, @Query("page")int page, @Query("count")int count);

    //查询用户信息接口
    @GET("small/user/verify/v1/getUserById")
    Observable<PersonBean> getResponsePersonData(@HeaderMap Map<String,String> map);

    //修改昵称接口
    @PUT("small/user/verify/v1/modifyUserNick")
    Observable<ResponseBody> getUpdateNameData(@HeaderMap Map<String,String> map, @Query("nickName")String name);

    //修改密码接口
    @PUT("small/user/verify/v1/modifyUserPwd")
    Observable<ResponseBody> getUpdatePwdData(@HeaderMap Map<String,String> map,@Query("oldPwd")String oldpwd,@Query("newPwd")String newpwd);

    //修改头像接口
    @Multipart
    @POST("small/user/verify/v1/modifyHeadPic")
    Observable<ResponseBody> getImageData(@HeaderMap Map<String,String> map, @Part MultipartBody.Part part);

    //商品详情接口
    @GET("small/commodity/v1/findCommodityDetailsById")
    io.reactivex.Observable<beans> getResponseData(@HeaderMap Map<String,String>map, @Query("commodityId")int id);

    //全网通缉接口
    @GET("getGoodsLink?appkey=c45726699b930ef75828ad53830d03fd&page=1&type=so")
    Observable<Taobao> getReponseTaobao(@Query("keyword")String name);
}
