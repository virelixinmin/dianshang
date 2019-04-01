package com.bw.project_demo.ui.fragment.gouwuche;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.project_demo.R;
import com.bw.project_demo.data.beans.FindShoppingBean;
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.ui.fragment.dingdan.fragment.payment.PayMentActivity;
import com.bw.project_demo.ui.fragment.gouwuche.beans.DingDanBean;
import com.bw.project_demo.ui.fragment.gouwuche.beans.FanBean;
import com.bw.project_demo.ui.fragment.gouwuche.beans.Order_Bean;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressBeans.AddressBean;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressConstance.AddressContance;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressPresenter.AddressPresenterImpl;
import com.bw.project_demo.ui.fragment.xiangqing.Details.DetailsActivity;
import com.bw.project_demo.ui.fragment.xiangqing.DetailsBeans.beans;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.lang.reflect.Array;
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

public class ShoppingActivity extends AppCompatActivity implements AddressContance.AddressView {

    @BindView(R.id.shouhuo)
    TextView shouhuo;
    @BindView(R.id.rcy_addres)
    RecyclerView rcyAddres;
    @BindView(R.id.data_rcy)
    RecyclerView dataRcy;
    @BindView(R.id.dingdan_btn)
    Button dingdanBtn;
    @BindView(R.id.all_price)
    TextView allPrice;
    private AddressContance.AddressPresenter presenter;
    private int alltotal;
    private List<FindShoppingBean.ResultBean> list;
    private int userId;
    private String sessionId;
    private int commodityId;
    private int countt;
    private int count;
    private DingDanBean dingDanBean;
    private String s;
    List<DingDanBean> dingDanBeanList = new ArrayList<>();
    List<FanBean> beanList = new ArrayList<>();
    private String s1;
    private int[] a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        ButterKnife.bind(this);
        initView();
        SharedPreferences data = getSharedPreferences("data", Context.MODE_PRIVATE);
        userId = data.getInt("userId", 0);
        sessionId = data.getString("sessionId", "");
        Intent intent = getIntent();
        list = (List<FindShoppingBean.ResultBean>) intent.getSerializableExtra("data");
        presenter = new AddressPresenterImpl();
        presenter.attahView(this);
        presenter.requestModel(userId, sessionId);

        for (int i = 0; i < list.size(); i++) {
//            a = new int[i];
//            a[i]=list.get(i).getPrice()* list.get(i).getCount();

            int price = list.get(i).getPrice();
            countt+=price*list.get(i).getCount();
            dingDanBean = new DingDanBean(list.get(i).getCommodityId(),list.get(i).getCount());
            dingDanBeanList.add(dingDanBean);
            alltotal+=price;

        }
        allPrice.setText("总价格是"+alltotal);
        MyShopDataAdapter adapter = new MyShopDataAdapter(R.layout.goods_layout_item, list);
        dataRcy.setLayoutManager(new LinearLayoutManager(ShoppingActivity.this));
        dataRcy.setAdapter(adapter);
    }

    private void initView() {
        shouhuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcyAddres.setVisibility(View.VISIBLE);
            }
        });
        dingdanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit build = new Retrofit.Builder()
                        .baseUrl(CheckPath.allString)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                ServiceApp myShoppingServiceApp = build.create(ServiceApp.class);
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId + "");
                map.put("sessionId", sessionId);
                HashMap<String, String> map1 = new HashMap<>();
                String path = "[{\"commodityId\":" + commodityId + ",\"amount\":" + count + "}]";
                Gson gson = new Gson();
                s = gson.toJson(dingDanBeanList);
                Toast.makeText(ShoppingActivity.this, ShoppingActivity.this.s+"____________"+s1, Toast.LENGTH_SHORT).show();
                map1.put("orderInfo", s);
                map1.put("totalPrice", countt+"");
                map1.put("addressId", String.valueOf(260));
                Observable<ResponseBody> buyResponseData = myShoppingServiceApp.getBuyResponseData(map, map1);
                buyResponseData.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
                                String s = responseBody.string().toString();
                                Gson gson1 = new Gson();
                                Order_Bean order_bean = gson1.fromJson(s, Order_Bean.class);
                                String orderId = order_bean.getOrderId();
                                Intent intent = new Intent(ShoppingActivity.this, PayMentActivity.class);
                                intent.putExtra("ord",orderId);
                                startActivity(intent);

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(ShoppingActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }

    @Override
    public void showData(final List<AddressBean.ResultBean> result) {
        ShoppingAdapter adapter = new ShoppingAdapter(R.layout.shop_layout_item, result);
        rcyAddres.setLayoutManager(new LinearLayoutManager(ShoppingActivity.this));
        rcyAddres.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                TextView btn = view.findViewById(R.id.default_tv);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = result.get(position).getId();
                        rcyAddres.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
