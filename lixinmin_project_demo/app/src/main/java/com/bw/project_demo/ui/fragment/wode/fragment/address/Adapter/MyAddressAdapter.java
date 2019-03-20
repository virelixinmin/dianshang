package com.bw.project_demo.ui.fragment.wode.fragment.address.Adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressBeans.AddressBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MyAddressAdapter extends BaseQuickAdapter<AddressBean.ResultBean,BaseViewHolder> {
    public MyAddressAdapter(int layoutResId, @Nullable List<AddressBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean.ResultBean item) {
        helper.setText(R.id.myaddress_item_name,item.getRealName());
        helper.setText(R.id.myaddress_item_phone,item.getPhone());
        helper.setText(R.id.myaddress_item_address,item.getAddress());
        helper.addOnClickListener(R.id.myaddress_item_updata);
        helper.addOnClickListener(R.id.myaddress_item_delete);
        helper.addOnClickListener(R.id.default_tv);
        Button dele = helper.getView(R.id.myaddress_item_delete);
        dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "删除不了哦亲", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
