package com.bw.project_demo.ui.fragment.xiangqing.Details;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.project_demo.R;
import com.bw.project_demo.data.beans.FindShoppingBean;
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.di.Contract.ShoppingContract;
import com.bw.project_demo.di.presenter.ShoppingPresenterImpl;
import com.bw.project_demo.ui.fragment.gouwuche.ShoppingActivity;
import com.bw.project_demo.ui.fragment.xiangqing.Car;
import com.bw.project_demo.ui.fragment.xiangqing.DetailsBeans.beans;
import com.bw.project_demo.ui.fragment.xiangqing.DetailsContrant.DetailsContrants;
import com.bw.project_demo.ui.fragment.xiangqing.Presenter.DetailsPresenterImpl;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class DetailsActivity extends AppCompatActivity implements DetailsContrants.DetailsView, ShoppingContract.ShoppingView {


    @BindView(R.id.goodsContentPage_ViewPager)
    XBanner goodsContentPageViewPager;
    @BindView(R.id.goodsContent_goods_Price)
    TextView goodsContentGoodsPrice;
    @BindView(R.id.goodsContent_goods_yiShou)
    TextView goodsContentGoodsYiShou;
    @BindView(R.id.goodsContent_goods_title)
    TextView goodsContentGoodsTitle;
    @BindView(R.id.goodsContent_goods_weight)
    TextView goodsContentGoodsWeight;
//    @BindView(R.id.goodsContent_product_contentImg)
//    SimpleDraweeView goodsContentProductContentImg;
//    @BindView(R.id.goodsContent_product_introductionText)
//    TextView goodsContentProductIntroductionText;
//    @BindView(R.id.goodsContent_product_introductionImg)
//    SimpleDraweeView goodsContentProductIntroductionImg;
    @BindView(R.id.shopping_care)
    ImageView shoppingCare;
    @BindView(R.id.buy_goods)
    ImageView buyGoods;
    @BindView(R.id.back_detail)
    ImageView backDetail;
    @BindView(R.id.web)
    WebView web;
    private DetailsContrants.DetailsPresenter presenter;
    private List<String> list;
    private SharedPreferences sp;
    private int id;
    private int count = 1;
    private List<FindShoppingBean.ResultBean> result;
    private List<Car> cars = new ArrayList<>();
    private String syncBody;
    private ShoppingPresenterImpl shoppingPresenter;
    beans beans;
    private com.bw.project_demo.ui.fragment.xiangqing.DetailsBeans.beans.ResultBean result1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", 6);
        Toast.makeText(this, "id:" + id, Toast.LENGTH_SHORT).show();
        sp = getSharedPreferences("data", Context.MODE_PRIVATE);
        final int userId = sp.getInt("userId", 634);
        final String sessionId = sp.getString("sessionId", "1550824775169634");
        presenter = new DetailsPresenterImpl();
        presenter.attahView(this);
        presenter.requestModel(userId, sessionId, id);
        shoppingPresenter = new ShoppingPresenterImpl();
        shoppingPresenter.attahView(this);
        shoppingPresenter.requestModel(userId, sessionId);
        backDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        ShoppingPresenterImpl shoppingPresenter = new ShoppingPresenterImpl();
//        shoppingPresenter.attahView(new ShoppingContract.ShoppingView() {
//            @Override
//            public void showData(FindShoppingBean shoppingBeans) {
//                result = shoppingBeans.getResult();
//
//
//                for (int i = 0; i < result.size(); i++) {
//                    Car car = new Car(result.get(i).getCommodityId(),result.get(i).getCount());
//                    cars.add(car);
//
//
//                }
//
//
//            }
//        });

        shoppingCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent in = new Intent(DetailsActivity.this,HomeActivity.class);
//                in.putExtra("id",1);
//                startActivity(in);
                // Toast.makeText(DetailsActivity.this, "添加成功,请去购物车查看", Toast.LENGTH_SHORT).show();

                Map<String, String> map = new HashMap<>();
                map.put("userId", userId + "");
                map.put("sessionId", sessionId);
                Map<String, String> map1 = new HashMap<>();


                cars.add(new Car(id,1));
                Gson gson = new Gson();
                String s = gson.toJson(cars);
                map1.put("data", s);
                RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
                .getShoppingResponseData(map, map1)
                .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
                                String string = responseBody.string();
                                Toast.makeText(DetailsActivity.this, string, Toast.LENGTH_SHORT).show();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                //Toast.makeText(DetailsActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        buyGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<FindShoppingBean.ResultBean> list= new ArrayList<>();
                FindShoppingBean.ResultBean resultBean = new FindShoppingBean.ResultBean();
                resultBean.setCommodityName(result1.getCategoryName());
                resultBean.setPic(result1.getPicture());
                resultBean.setPrice(result1.getPrice());
                resultBean.setCount(result1.getCommentNum());
                list.add(new FindShoppingBean.ResultBean(result1.getCommodityId(),result1.getCommodityName(),1,result1.getPicture(),result1.getPrice(),true));
                Log.e("gsk", "list:----------- "+list );
                Intent in = new Intent(DetailsActivity.this,ShoppingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", (Serializable) list);
                in.putExtras(bundle);
                startActivity(in);
//
            }
        });
    }

    @Override
    public void showData(FindShoppingBean shoppingBeans) {
        result = shoppingBeans.getResult();
        for (int i = 0; i < result.size(); i++) {
            Car car = new Car(result.get(i).getCommodityId(), result.get(i).getCount());
            cars.add(car);
        }
    }



    @Override
    public void showData(beans beans) {
        this.beans = beans;
        result1 = beans.getResult();
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        // web.setWebViewClient(new WebViewClient());
        //   web.loadUrl(beans.getResult().getDetails());
        String s="<script type=\"text/javascript\">" +
                "var imgs=document.getElementsByTagName('img');" +
                "for(var i = 0; i<imgs.length; i++){" +
                "imgs[i].style.width='100%';" +
                "imgs[i].style.height='auto';" +
                "}" +
                "</script>";
        String details = beans.getResult().getDetails();
        web.loadDataWithBaseURL(null,details+s+"<html><body>","text/html","utf-8",null);

        list = new ArrayList<>();
        String picture = beans.getResult().getPicture();
        final String[] split = picture.split(",");
        for (int i = 0; i < split.length; i++) {
            String l = split[i];
            list.add(l);
        }

        Uri parse = Uri.parse(list.get(3));
//        goodsContentProductContentImg.setImageURI(parse);
        goodsContentPageViewPager.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(DetailsActivity.this).load(split[position]).into((ImageView) view);
            }
        });

//        Uri parse1 = Uri.parse(list.get(4));
//        goodsContentProductIntroductionImg.setImageURI(parse1);
        goodsContentPageViewPager.setData(list, null);
        goodsContentGoodsPrice.setText(beans.getResult().getPrice() + "");
        goodsContentGoodsTitle.setText(beans.getResult().getCommodityName());
        goodsContentGoodsYiShou.setText(beans.getResult().getCommentNum());
//        goodsContentProductIntroductionText.setText(beans.getResult().getDescribe());

    }


}
