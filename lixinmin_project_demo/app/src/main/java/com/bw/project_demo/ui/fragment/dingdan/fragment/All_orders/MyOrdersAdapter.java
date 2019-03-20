package com.bw.project_demo.ui.fragment.dingdan.fragment.All_orders;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.project_demo.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.IOException;
import java.util.List;

public class MyOrdersAdapter extends BaseQuickAdapter<All_ordersbean.OrderListBean.DetailListBean,BaseViewHolder> {
    public MyOrdersAdapter(int layoutResId, @Nullable List<All_ordersbean.OrderListBean.DetailListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, final All_ordersbean.OrderListBean.DetailListBean item) {

            helper.setText(R.id.tv_name,item.getCommodityName());
            helper.setText(R.id.tv_price,"$"+item.getCommodityPrice());
            int commodityCount = item.getCommodityCount();
            int i1 = item.getCommodityCount() * item.getCommodityPrice();
            myOrder.getData(commodityCount,i1);
            helper.setText(R.id.tv_num,item.getCommodityCount()+"");
            ImageView img = helper.getView(R.id.img_shopping);
            String commodityPic = item.getCommodityPic();
            String[] split = commodityPic.split(",");
            for (int i = 0; i < split.length; i++) {
                Glide.with(mContext).load(split[i]).into(img);
            }

        ImageButton img_jia = helper.getView(R.id.jia);
        ImageButton img_jian = helper.getView(R.id.jian);
        img_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int saleNum = item.getCommodityCount();
                int i=saleNum+1;
                item.setCommodityCount(i);
                notifyDataSetChanged();


            }
        });
        img_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int saleNum = item.getCommodityCount();
                int i = saleNum - 1;
                if (i < 0) {
                    i = 0;
                    item.setCommodityCount(i);
                }
                item.setCommodityCount(i);
                notifyDataSetChanged();

            }
        });
    }

    public interface MyOrdersCallBack{
        public void getData(int count, int commodityCount);
    }
    MyOrdersCallBack myOrder;
    public void setOnMyOrder(MyOrdersCallBack myOrder){
        this.myOrder=myOrder;
    }
}
