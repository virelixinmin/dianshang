package com.bw.project_demo.ui.fragment.dingdan.fragment.receiving;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.dingdan.fragment.All_orders.All_ordersbean;
import com.bw.project_demo.ui.fragment.dingdan.fragment.All_orders.MyOrdersAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MyReceiving extends BaseQuickAdapter<All_ordersbean.OrderListBean,BaseViewHolder> {
    public MyReceiving(int layoutResId, @Nullable List<All_ordersbean.OrderListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, All_ordersbean.OrderListBean item) {
        RecyclerView recy = helper.getView(R.id.child_recy);
        helper.setText(R.id.order_PendingPayment_dd,item.getOrderId());
        helper.setText(R.id.order_PendingPayment_num,item.getPayAmount()+"");
        helper.setText(R.id.address_pany,item.getExpressCompName());
        Button btn = helper.getView(R.id.order_PendingPayment_payment);
        List<All_ordersbean.OrderListBean.DetailListBean> detailList = item.getDetailList();
        MyOrdersAdapter adapter = new MyOrdersAdapter(R.layout.goods_item,detailList);
        recy.setLayoutManager(new LinearLayoutManager(mContext));
        recy.setAdapter(adapter);
        adapter.setOnMyOrder(new MyOrdersAdapter.MyOrdersCallBack() {
            @Override
            public void getData(int count, int commodityCount) {

            }
        });
        helper.addOnClickListener(R.id.order_PendingPayment_payment);
    }
}
