package com.bw.project_demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.project_demo.R;
import com.bw.project_demo.data.adapter.MyFashionAdapter;
import com.bw.project_demo.data.adapter.MyMlssAdapter;
import com.bw.project_demo.data.adapter.MyPinzhiAdapter;
import com.bw.project_demo.data.beans.BannerBeans;
import com.bw.project_demo.data.beans.ShopBeans;
import com.bw.project_demo.di.Contract.ContractAll;
import com.bw.project_demo.di.presenter.BannerPresenterImpl;
import com.bw.project_demo.ui.activity.HomeActivity;
import com.bw.project_demo.ui.fragment.search.SearchActivity;
import com.bw.project_demo.ui.fragment.xiangqing.Details.DetailsActivity;
import com.lxj.xpopup.XPopup;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FirstFragment extends Fragment implements ContractAll.BannerView {

    Unbinder unbinder;
    @BindView(R.id.mBanner)
    XBanner mBanner;
    @BindView(R.id.neirong)
    ImageButton neirong;
//    @BindView(R.id.huoqu)
//    ImageButton huoqu;
    @BindView(R.id.hotShop)
    RecyclerView hotShop;
    @BindView(R.id.FashionShop)
    RecyclerView FashionShop;
    @BindView(R.id.pinzhiShop)
    RecyclerView pinzhiShop;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.edit)
    Button edit;


    private ContractAll.BannerPresenter presenter;
    private List<BannerBeans.ResultBean> result;

    private List<String> titles;


    private int commodityId;
    private List<ShopBeans.ResultBean.MlssBean.CommodityListBeanXX> mlss;
    private List<ShopBeans.ResultBean.PzshBean.CommodityListBeanX> pzsh;
    private List<ShopBeans.ResultBean.RxxpBean.CommodityListBean> rxx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.fitst_item, null);

        unbinder = ButterKnife.bind(this, v);

        presenter = new BannerPresenterImpl();
        presenter.attchView(this);
        presenter.requestModel();
        presenter.ShopAttchView(this);
        presenter.ShoprequestModel();

        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
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
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SousuoActivity.class));
            }
        });
//       huoqu.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               if (edit.getText().toString().equals("")){
//                   Toast.makeText(getActivity(), "搜索框不能为空", Toast.LENGTH_SHORT).show();
//               }else{
//                   Intent in = new Intent(getActivity(),SearchActivity.class);
//                   in.putExtra("search_name",edit.getText().toString());
//                   startActivity(in);
//               }
//
//           }
//       });
        neirong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XPopup.get(getActivity())
                        .asCustom(new Xpopuwindow(getActivity()))
                        .customAnimator(new RotateAnimator())
                        .show();

            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void ShowData(BannerBeans bannerBeans) {
        result = bannerBeans.getResult();
        List<String> list = new ArrayList<>();
        for (int i = 0; i <result.size() ; i++) {
            list.add(result.get(i).getImageUrl());
        }
        mBanner.setData(list, null);
        mBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getActivity()).load(result.get(position).getImageUrl()).into((ImageView) view);
            }
        });
        mBanner.setIsClipChildrenMode(true);


//        //设置banner样式
//        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
//        //设置图片加载器
//        mBanner.setImageLoader(new GildeImageLoader());
//        //设置图片集合
//        mBanner.setImages(list);
//        //设置banner动画效果
//        mBanner.setBannerAnimation(Transformer.RotateDown);
//        //设置标题集合（当banner样式有显示title时）
//        mBanner.setBannerTitles(titles);
//        //设置轮播时间
//        // mBanner.setDelayTime(1500);
//        //设置指示器位置（当banner模式中有指示器时）
//        mBanner.setIndicatorGravity(BannerConfig.CENTER);
//        //banner设置方法全部调用完毕时最后调用
//        mBanner.start();
    }

    @Override
    public void ShopShowData(ShopBeans shopBeans) {

        //魔力时尚
        mlss = shopBeans.getResult().getMlss().getCommodityList();


        //品质生活
        pzsh = shopBeans.getResult().getPzsh().getCommodityList();


        //热销新品
        rxx = shopBeans.getResult().getRxxp().getCommodityList();




        initRxxpData(rxx);
        //魔力时尚
        initFashion(mlss);
        //品质生活
        initPinzhi(pzsh);
    }

    private void initPinzhi(List<ShopBeans.ResultBean.PzshBean.CommodityListBeanX> pzsh) {
        MyPinzhiAdapter adapter = new MyPinzhiAdapter(getActivity(), pzsh);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        pinzhiShop.setLayoutManager(manager);
        pinzhiShop.setAdapter(adapter);
        adapter.setOnImgClickListen(new MyPinzhiAdapter.OnpinzhiImgClickListen() {
            @Override
            public void OnClick(int commodityId) {
                Intent in = new Intent(getActivity(), DetailsActivity.class);
                in.putExtra("id", commodityId);

                startActivity(in);
            }
        });

    }

    private void initFashion(List<ShopBeans.ResultBean.MlssBean.CommodityListBeanXX> mlss) {
        MyFashionAdapter adapter = new MyFashionAdapter(getActivity(), mlss);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        FashionShop.setLayoutManager(manager);
        FashionShop.setAdapter(adapter);

        adapter.setOnImgClickListen(new MyFashionAdapter.OnfashionImgClickListen() {
            @Override
            public void OnClick(int commodityId) {
                Intent in = new Intent(getActivity(), DetailsActivity.class);
                in.putExtra("id", commodityId);
                startActivity(in);
            }
        });

    }

    //热销新品
    private void initRxxpData(List<ShopBeans.ResultBean.RxxpBean.CommodityListBean> rxxp) {
        MyMlssAdapter adapter = new MyMlssAdapter(getActivity(), rxxp);
//        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
//
//        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        GridLayoutManager manager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
        hotShop.setLayoutManager(manager);
        // Toast.makeText(getActivity(), "mlss:" + mlss, Toast.LENGTH_SHORT).show();

        hotShop.setAdapter(adapter);

        adapter.setOnImgClickListen(new MyMlssAdapter.OnImgClickListen() {
            @Override
            public void OnClick(int commodityId) {
                Intent in = new Intent(getActivity(), DetailsActivity.class);
                in.putExtra("id", commodityId);
                startActivity(in);
            }
        });
    }


}
