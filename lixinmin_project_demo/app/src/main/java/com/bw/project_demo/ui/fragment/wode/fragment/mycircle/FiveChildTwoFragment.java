package com.bw.project_demo.ui.fragment.wode.fragment.mycircle;

import android.app.Activity;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.project_demo.R;

import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.adapter.MyCircleAdapter;
import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.beans.circlebean;
import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.circleConstance.CircleConstance;
import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.presenter.CirclePresenterImpl;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.whyalwaysmea.circular.AnimUtils;

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

public class FiveChildTwoFragment extends Fragment implements CircleConstance.CircleView {

    @BindView(R.id.my_group_ryl)
    RecyclerView myGroupRyl;
    Unbinder unbinder;
    @BindView(R.id.my_group_setting)
    TextView myGroupSetting;
    @BindView(R.id.my_group_delete)
    ImageView myGroupDelete;
    @BindView(R.id.recy_refrashss)
    SmartRefreshLayout recyRefrashss;
    private CircleConstance.CirclePresenter presenter;
    private String sessionId;
    private int userId;
    private int commodityId;
    private StringBuffer stringBuffer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getActivity(), R.layout.five_child_two, null);

        SharedPreferences data = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        sessionId = data.getString("sessionId", "15011445417");
        userId = data.getInt("userId", 184);
        presenter = new CirclePresenterImpl();
        presenter.attahView(this);
        presenter.requestModel(sessionId, userId);
        unbinder = ButterKnife.bind(this, inflate);
        myGroupSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), SendActivity.class);
                AnimUtils.startIntent(in,v, getActivity(),R.id.my_group_ryl);
            }
        });

        recyRefrashss.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                presenter.requestModel(sessionId,userId);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败

            }
        });
        recyRefrashss.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        //设置 Header 为 贝塞尔雷达 样式
        recyRefrashss.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        recyRefrashss.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));


        return inflate;
    }

    @Override
    public void showData(final List<circlebean.ResultBean> result) {
        MyCircleAdapter adapter;
        adapter = new MyCircleAdapter(R.layout.circle_child_item, result, myGroupDelete);
        myGroupRyl.setLayoutManager(new LinearLayoutManager(getActivity()));
        myGroupRyl.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                final CheckBox checkBox = view.findViewById(R.id.chebox);
                if (checkBox.isChecked()) {
                    commodityId = result.get(position).getId();
                    Toast.makeText(getActivity(), "commodityId:" + commodityId, Toast.LENGTH_SHORT).show();
                }

                myGroupDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkBox.isChecked()) {

                            HashMap<String, String> map = new HashMap<>();
                            map.put("userId", userId + "");
                            map.put("sessionId", sessionId);
                            stringBuffer = new StringBuffer();
                            stringBuffer.append(commodityId + ",");
                            String s = stringBuffer.toString();
                            String substring = s.substring(0, s.length() - 1);
                            int i = Integer.parseInt(substring);
                            RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
                                    .getResponseBodydele(map,i)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<ResponseBody>() {
                                        @Override
                                        public void accept(ResponseBody responseBody) throws Exception {
                                            String s = responseBody.string().toString();
                                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });


            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
