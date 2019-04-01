package com.bw.project_demo.ui.fragment.dingdan.fragment;

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

import com.bw.project_demo.R;
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.ui.fragment.dingdan.fragment.All_orders.All_ordersbean;
import com.bw.project_demo.ui.fragment.dingdan.fragment.All_orders.My_Alls_Adapter;
import com.bw.project_demo.data.contractPath.ServiceApp;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
//待评价
public class remain extends Fragment {
    @BindView(R.id.rcy_ping)
    RecyclerView rcyPing;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getActivity(), R.layout.remain, null);

        SharedPreferences data = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        final int userId = data.getInt("userId", 5);
        final String sessionId = data.getString("sessionId", "15011445417");
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId + "");
        map.put("sessionId", sessionId);
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("status", 3 + "");
        map2.put("page", 1 + "");
        map2.put("count", 5 + "");
        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
                .getorders(map,map2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<All_ordersbean>() {
                    @Override
                    public void accept(All_ordersbean all_ordersbean) throws Exception {
                        List<All_ordersbean.OrderListBean> orderList = all_ordersbean.getOrderList();
                        My_Alls_Adapter adapter = new My_Alls_Adapter(R.layout.my_alls, orderList);
                        rcyPing.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rcyPing.setAdapter(adapter);

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
