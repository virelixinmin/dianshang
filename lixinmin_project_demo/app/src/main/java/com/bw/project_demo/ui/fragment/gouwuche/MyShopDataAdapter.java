package com.bw.project_demo.ui.fragment.gouwuche;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.project_demo.R;
import com.bw.project_demo.data.beans.FindShoppingBean;
import com.bw.project_demo.ui.fragment.xiangqing.DetailsBeans.beans;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MyShopDataAdapter extends BaseQuickAdapter<FindShoppingBean.ResultBean,BaseViewHolder> {
    public MyShopDataAdapter(int layoutResId, @Nullable List<FindShoppingBean.ResultBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, FindShoppingBean.ResultBean item) {
        String[] split = item.getPic().split(",");
        helper.setText(R.id.tv_price,"$"+item.getPrice());
        helper.setText(R.id.tv_name,item.getCommodityName());
        Glide.with(mContext).load(split[0]).into((ImageView) helper.getView(R.id.img_shopping));
    }
}
