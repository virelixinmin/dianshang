package com.bw.project_demo.ui.fragment;

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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.project_demo.R;
import com.bw.project_demo.data.adapter.MyBussionAdapter;
import com.bw.project_demo.data.beans.FindShoppingBean;
import com.bw.project_demo.di.Contract.ShoppingContract;
import com.bw.project_demo.di.presenter.ShoppingPresenterImpl;
import com.bw.project_demo.ui.fragment.gouwuche.ShoppingContent.ShoppingCon;
import com.bw.project_demo.ui.fragment.gouwuche.widght.MyShoppingServiceApp;
import com.bw.project_demo.ui.fragment.xiangqing.Details.DetailsActivity;
import com.bw.project_demo.ui.fragment.xiangqing.DetailsBeans.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class ThreeFragment extends Fragment implements ShoppingContract.ShoppingView {


    @BindView(R.id.recy_item)
    RecyclerView recyItem;
    Unbinder unbinder;
    @BindView(R.id.cb_quanxuan)
    CheckBox cbQuanxuan;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.jiesuan)
    Button jiesuan;
    private ShoppingContract.ShoppingPresenter presenter;
    Context mContext = getActivity();
    private SharedPreferences sp;
    private List<FindShoppingBean.ResultBean> data;
    private int[] startLocation;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = View.inflate(getActivity(), R.layout.three_item, null);
        startLocation = new int[2];

        unbinder = ButterKnife.bind(this, v);
        presenter = new ShoppingPresenterImpl();
        presenter.attahView(this);
        sp = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        final int userId = sp.getInt("userId", 18);
        final String sessionId = sp.getString("sessionId", "15011445417");
        presenter.requestModel(userId, sessionId);
        jiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(getActivity(), "创建订单成功,请手动跳转全部订单页面", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void showData(FindShoppingBean shoppingBeans) {

        data = shoppingBeans.getResult();
        final MyBussionAdapter adapter = new MyBussionAdapter(R.layout.goods_item, data);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyItem.setLayoutManager(manager);
        recyItem.setAdapter(adapter);
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setCount(1);
        }
        adapter.setOnClickListen(new MyBussionAdapter.BussionCallBack() {
            @Override
            public void onCallBack() {
                boolean result = true;
                for (int i = 0; i < data.size(); i++) {
                    boolean onGoodsChecked = data.get(i).getChecked();
                    result = result & onGoodsChecked;
                }
                cbQuanxuan.setChecked(result);
                //adapter.notifyDataSetChanged();
                calculateTotalCount();
            }
        });

        cbQuanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setChecked(cbQuanxuan.isChecked());
                }
                adapter.notifyDataSetChanged();
                calculateTotalCount();
            }
        });
    }

    private void calculateTotalCount() {
        int totalCount = 0;
        for (int i = 0; i < data.size(); i++) {

            if (data.get(i).getChecked() == true) {
                int saleNum = data.get(i).getCount();
                int price = data.get(i).getPrice();
                int i1 = saleNum * price;
                totalCount = totalCount + i1;
            }
        }
        tvPrice.setText("总金额是$" + totalCount);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
