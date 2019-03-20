package com.bw.project_demo.ui.fragment.dingdan.fragment.All_orders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.dingdan.fragment.payment.PayMentActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class All_orders extends Fragment {


    @BindView(R.id.all_rcy)
    RecyclerView allRcy;
    Unbinder unbinder;
    private My_Alls_Adapter adapter;
    private String ord;
    private int userId;
    private String sessionId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getActivity(), R.layout.all_list, null);
        unbinder = ButterKnife.bind(this, inflate);
        SharedPreferences data = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        userId = data.getInt("userId", 5);
        sessionId = data.getString("sessionId", "15011445417");
        Retrofit build = new Retrofit.Builder().baseUrl("http://mobile.bwstudent.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId + "");
        map.put("sessionId", sessionId);
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("status", 0 + "");
        map2.put("page", 1 + "");
        map2.put("count", 5 + "");
        ServiceApp serviceApp = build.create(ServiceApp.class);
        Observable<All_ordersbean> getorders = serviceApp.getorders(map, map2);
        getorders.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<All_ordersbean>() {
                    @Override
                    public void accept(All_ordersbean all_ordersbean) throws Exception {
                        List<All_ordersbean.OrderListBean> orderList = all_ordersbean.getOrderList();
                        adapter = new My_Alls_Adapter(R.layout.my_alls,orderList);
                        allRcy.setLayoutManager(new LinearLayoutManager(getActivity()));
                        allRcy.setAdapter(adapter);
                        adapter.setOnAllCallBack(new My_Alls_Adapter.AllsCallBack() {
                            @Override
                            public void getData(String orderId) {

                                ord = orderId;
                            }
                        });

                        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(final BaseQuickAdapter adapter, View view, int position) {
                                Button cancel = view.findViewById(R.id.order_PendingPayment_cancel);
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Retrofit build1 = new Retrofit.Builder()
                                                .baseUrl("http://mobile.bwstudent.com/")
                                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                                .build();
                                        ServiceApp serviceApp1 = build1.create(ServiceApp.class);
                                        HashMap<String, String> map = new HashMap<>();
                                        map.put("userId", userId + "");
                                        map.put("sessionId", sessionId);
                                        Observable<ResponseBody> delete = serviceApp1.getDelete(map,ord);
                                        delete.subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Consumer<ResponseBody>() {
                                                    @Override
                                                    public void accept(ResponseBody responseBody) throws Exception {
                                                        Toast.makeText(getActivity(), responseBody.string(), Toast.LENGTH_SHORT).show();

                                                    }
                                                }, new Consumer<Throwable>() {
                                                    @Override
                                                    public void accept(Throwable throwable) throws Exception {

                                                    }
                                                });
                                    }
                                });
                            }
                        });
                        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                Button payment = view.findViewById(R.id.order_PendingPayment_payment);
                                payment.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent in = new Intent(getActivity(),PayMentActivity.class);
                                        in.putExtra("ord",ord);
                                        startActivity(in);
                                    }
                                });
                            }
                        });
                        adapter.notifyDataSetChanged();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });





        return inflate;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
