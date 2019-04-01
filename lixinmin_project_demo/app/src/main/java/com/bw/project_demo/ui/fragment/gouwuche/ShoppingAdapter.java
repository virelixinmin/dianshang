package com.bw.project_demo.ui.fragment.gouwuche;

import android.support.annotation.Nullable;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressBeans.AddressBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ShoppingAdapter extends BaseQuickAdapter<AddressBean.ResultBean,BaseViewHolder> {
    public ShoppingAdapter(int layoutResId, @Nullable List<AddressBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean.ResultBean item) {
        helper.setText(R.id.myaddress_item_name,item.getRealName());
        helper.setText(R.id.myaddress_item_phone,item.getPhone());
        helper.setText(R.id.myaddress_item_address,item.getAddress());
        helper.addOnClickListener(R.id.default_tv);
    }
}
