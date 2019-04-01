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

import com.bw.project_demo.R;
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.ui.fragment.SousuoActivity;
import com.bw.project_demo.ui.fragment.dingdan.fragment.payment.PayMentActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

//全部
public class All_orders extends Fragment {


    @BindView(R.id.all_rcy)
    RecyclerView allRcy;
    Unbinder unbinder;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private My_Alls_Adapter adapter;
    private String ord;
    private int userId;
    private String sessionId;
    private HashMap<String, String> map;
    private HashMap<String, String> map2;


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

        map = new HashMap<>();
        map.put("userId", userId + "");
        map.put("sessionId", sessionId);
        map2 = new HashMap<>();
        map2.put("status", 0 + "");
        map2.put("page", 1 + "");
        map2.put("count", 5 + "");
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                InterNet();
            }
        });
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        //设置 Header 为 贝塞尔雷达 样式
        refresh.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        refresh.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));

        InterNet();


        return inflate;
    }

    private void InterNet() {
        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString, ServiceApp.class)
                .getorders(map, map2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<All_ordersbean>() {
                    @Override
                    public void accept(All_ordersbean all_ordersbean) throws Exception {
                        final List<All_ordersbean.OrderListBean> orderList = all_ordersbean.getOrderList();
                        adapter = new My_Alls_Adapter(R.layout.my_alls, orderList);
                        allRcy.setLayoutManager(new LinearLayoutManager(getActivity()));
                        allRcy.setAdapter(adapter);

                        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                Button payment = view.findViewById(R.id.order_PendingPayment_payment);
                                final String orderId = orderList.get(position).getOrderId();
                                payment.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent in = new Intent(getActivity(), PayMentActivity.class);
                                        in.putExtra("ord", orderId);
                                        startActivity(in);
                                    }
                                });
                            }
                        });
                        adapter.notifyDataSetChanged();
                    }
                });
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
