package com.bw.project_demo.ui.fragment.dingdan.fragment.All_orders;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bw.project_demo.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.reactivex.functions.Consumer;

public class My_Alls_Adapter extends BaseQuickAdapter<All_ordersbean.OrderListBean,BaseViewHolder> {
    List<All_ordersbean.OrderListBean> data;
    private MyOrdersAdapter adapter;


    public interface AllsCallBack{
        public void getData(String orderId);
    }
    AllsCallBack allCallBack;
    public void setOnAllCallBack(AllsCallBack allCallBack){
        this.allCallBack=allCallBack;
    }
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

        String orderId = item.getOrderId();
        allCallBack.getData(orderId);
        adapter.setOnMyOrder(new MyOrdersAdapter.MyOrdersCallBack() {
            @Override
            public void getData(int count, int commodityCount) {
                helper.setText(R.id.order_PendingPayment_size,count+"");
                helper.setText(R.id.order_PendingPayment_price,commodityCount+"");
            }
        });

        helper.addOnClickListener(R.id.order_PendingPayment_cancel);
        helper.addOnClickListener(R.id.order_PendingPayment_payment);

    }

}
