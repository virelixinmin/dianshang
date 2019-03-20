package com.bw.project_demo.ui.fragment.quanzi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.quanzi.Adapter.MyCircleAdapter;
import com.bw.project_demo.ui.fragment.quanzi.bean.CircleBeans;
import com.bw.project_demo.ui.fragment.quanzi.contance.CircleData;
import com.bw.project_demo.ui.fragment.quanzi.presenter.CirclePresenterImpl;
import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.SendActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TwoFragment extends Fragment implements CircleData.CircleView {
    @BindView(R.id.circle_rcy)
    RecyclerView circleRcy;
    Unbinder unbinder;
    @BindView(R.id.tv_fabu)
    TextView tvFabu;
    @BindView(R.id.recy_refrash)
    SmartRefreshLayout recyRefrash;
    private CircleData.CirclePresenter circlePresenter;
    private List<CircleBeans.ResultBean> result;
    private int page = 1;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.two_item, container, false);
        circlePresenter = new CirclePresenterImpl();
        circlePresenter.attahView(this);
        circlePresenter.requestData(page);

        unbinder = ButterKnife.bind(this, v);

        tvFabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SendActivity.class));
            }
        });


        recyRefrash.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=1;
                circlePresenter.requestData(page);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败

            }
        });
        recyRefrash.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        //设置 Header 为 贝塞尔雷达 样式
        recyRefrash.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        recyRefrash.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));


        return v;
    }

    @Override
    public void showData(CircleBeans circleBeans) {
        result = circleBeans.getResult();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        circleRcy.setLayoutManager(manager);
        final MyCircleAdapter adapter = new MyCircleAdapter(R.layout.circle_item, result);
        circleRcy.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //点赞
//        adapter.setOnItemHandClickListner(new MyCircleAdapter.OnItemHandClickListner() {
//            @Override
//            public void click(int id,int whetherGreatId) {
//                //没有点过赞的去点赞
//                if (whetherGreatId==2){
//                    //设置小手
//
//                    for (int i = 0; i < result.size(); i++) {
//                        CircleBeans.ResultBean resultBean = result.get(i);
//                        if (id==resultBean.getId()){
//                            resultBean.setWhetherGreat(1);
//                            resultBean.setGreatNum(resultBean.getGreatNum()+1);
//                        }
//                    }
//                    adapter.notifyDataSetChanged();
//                    //去请求数据点赞
////                    Login.ResultBean spData = SpUtil.getSpData();
////                    myCirclePresenter.setPreGreatCircleUrl(spData.getUserId(),spData.getSessionId(),id);
//                }
//                //点过赞的去取消点赞
//                else {
//                    //设置小手
//
//                    for (int i = 0; i < result.size(); i++) {
//                        CircleBeans.ResultBean resultBean = result.get(i);
//                        if (id==resultBean.getId()){
//                            resultBean.setWhetherGreat(2);
//                            resultBean.setGreatNum(resultBean.getGreatNum()-1);
//                        }
//                    }
//                    adapter.notifyDataSetChanged();
//                    //去请求数据点赞
////                    Login.ResultBean spData = SpUtil.getSpData();
////                    myCirclePresenter.setPreNoGreatCircleUrl(spData.getUserId(),spData.getSessionId(),id);
//                }
//
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onResume() {
        super.onResume();
        circlePresenter.requestData(page);
    }
}
