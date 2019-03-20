package com.bw.project_demo.ui.fragment.search;

import android.content.Intent;
import android.database.Observable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.bw.project_demo.R;
import com.bw.project_demo.data.beans.Taobao;
import com.bw.project_demo.ui.fragment.xiangqing.MyServiceApp;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchErrorActivity extends AppCompatActivity {

    @BindView(R.id.btn_tiaozhuan)
    Button btnTiaozhuan;
    @BindView(R.id.web)
    WebView web;
    private String shop;
    private String goods_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_error);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        shop = intent.getStringExtra("shop");


        btnTiaozhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTiaozhuan.setVisibility(View.GONE);
                WebSettings settings = web.getSettings();
                settings.setJavaScriptEnabled(true);
                web.setVisibility(View.VISIBLE);
                web.setWebChromeClient(new WebChromeClient());
                Retrofit build = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .baseUrl("http://api.tkjidi.com/")
                        .build();
                MyServiceApp myServiceApp = build.create(MyServiceApp.class);
                io.reactivex.Observable<Taobao> reponseTaobao = myServiceApp.getReponseTaobao(shop);
                reponseTaobao.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Taobao>() {
                            @Override
                            public void accept(Taobao taobao) throws Exception {
                                String goods_url = taobao.getData().get(0).getGoods_url();
                                Toast.makeText(SearchErrorActivity.this, goods_url, Toast.LENGTH_SHORT).show();
                                web.loadUrl(goods_url);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        });
//                OkGo.<String>get("http://api.tkjidi.com/getGoodsLink?appkey=c45726699b930ef75828ad53830d03fd&page=1&type=so&keyword="+shop).execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        String body = response.body();
//                        Toast.makeText(SearchErrorActivity.this, body, Toast.LENGTH_SHORT).show();
//                        Gson g = new Gson();
//                        Taobao taobao = g.fromJson(body, Taobao.class);
//                         goods_url = taobao.getData().get(0).getGoods_url();
////                        for (int i = 0; i < data.size(); i++) {
////                            goods_url = data.get(i).getGoods_url();
////                        }
//                        web.loadUrl(goods_url);
//                    }
//                });

//                String s="<script type=\"text/javascript\">" +
//
//                        "var imgs=document.getElementsByTagName('img');" +
//                        "for(var i = 0; i<imgs.length; i++){" +
//                        "imgs[i].style.width='100%';" +
//                        "imgs[i].style.height='auto';" +
//                        "}" +
//                        "</script>";
//                web.loadDataWithBaseURL(null,goods_url+s+"<html><body>","text/html","utf-8",null);

            }
        });
    }
}
