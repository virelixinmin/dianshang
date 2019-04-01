package com.bw.project_demo.ui.fragment.dingdan.fragment.receiving;

import android.app.Service;
import android.content.Context;
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
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.ui.fragment.dingdan.fragment.All_orders.All_ordersbean;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.chad.library.adapter.base.BaseQuickAdapter;

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
//待收货
public class receiving extends Fragment {
    @BindView(R.id.receiving_rcy)
    RecyclerView receivingRcy;
    Unbinder unbinder;
    private int userId;
    private String sessionId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getActivity(), R.layout.receiving, null);
        SharedPreferences data = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        userId = data.getInt("userId", 5);
        sessionId = data.getString("sessionId", "15011445417");
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId + "");
        map.put("sessionId", sessionId);
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("status", 2 + "");
        map2.put("page", 1 + "");
        map2.put("count", 5 + "");
        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
                .getorders(map,map2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<All_ordersbean>() {
                    @Override
                    public void accept(All_ordersbean all_ordersbean) throws Exception {
                        final List<All_ordersbean.OrderListBean> orderList = all_ordersbean.getOrderList();
                        MyReceiving adapter = new MyReceiving(R.layout.my_receiving, orderList);
                        receivingRcy.setLayoutManager(new LinearLayoutManager(getActivity()));
                        receivingRcy.setAdapter(adapter);

                        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                                Button btn = view.findViewById(R.id.order_PendingPayment_payment);
                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String orderId = orderList.get(position).getOrderId();


                                        HashMap<String, String> map = new HashMap<>();
                                        map.put("userId", userId + "");
                                        map.put("sessionId", sessionId);

                                        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
                                                .getReceiving(map,orderId)
                                                .subscribeOn(Schedulers.io())
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
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {


                    }
                });

        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
