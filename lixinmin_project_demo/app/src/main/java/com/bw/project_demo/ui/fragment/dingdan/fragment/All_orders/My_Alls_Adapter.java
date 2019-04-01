package com.bw.project_demo.ui.fragment.dingdan.fragment.All_orders;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bw.project_demo.R;
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class My_Alls_Adapter extends BaseQuickAdapter<All_ordersbean.OrderListBean,BaseViewHolder> {
    List<All_ordersbean.OrderListBean> data;
    private MyOrdersAdapter adapter;
    public My_Alls_Adapter(int layoutResId, @Nullable List<All_ordersbean.OrderListBean> data) {
        super(layoutResId, data);
        this.data=data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, All_ordersbean.OrderListBean item) {
        helper.setText(R.id.order_PendingPayment_number,item.getOrderId());

        helper.setText(R.id.order_PendingPayment_size,item.getPayAmount()+"");
        helper.setText(R.id.order_PendingPayment_time,item.getPayAmount()+"");
        RecyclerView child_rcy = helper.getView(R.id.order_PendingPayment_recycle);
        List<All_ordersbean.OrderListBean.DetailListBean> detailList = item.getDetailList();
        adapter = new MyOrdersAdapter(R.layout.goods_item,detailList);
        child_rcy.setLayoutManager(new LinearLayoutManager(mContext));
        child_rcy.setAdapter(adapter);



        adapter.setOnMyOrder(new MyOrdersAdapter.MyOrdersCallBack() {
            @Override
            public void getData(int count, int commodityCount) {
                helper.setText(R.id.order_PendingPayment_size,count+"");
                helper.setText(R.id.order_PendingPayment_price,commodityCount+"");
            }
        });
        SharedPreferences data = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        final int userId = data.getInt("userId", 5);
        final String sessionId = data.getString("sessionId", "15011445417");
        helper.addOnClickListener(R.id.order_PendingPayment_cancel);
        Button btn_cancel = helper.getView(R.id.order_PendingPayment_cancel);
        final String orderId = item.getOrderId();
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();
                map.put("userId", userId + "");
                map.put("sessionId", sessionId);
                Observable<ResponseBody> delete = RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString, ServiceApp.class)
                        .getDelete(map, orderId);
                delete.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
                                Toast.makeText(mContext, responseBody.string(), Toast.LENGTH_SHORT).show();

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        });
            }
        });
        helper.addOnClickListener(R.id.order_PendingPayment_payment);



    }

}
