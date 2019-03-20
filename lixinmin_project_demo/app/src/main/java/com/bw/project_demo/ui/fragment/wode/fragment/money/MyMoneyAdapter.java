package com.bw.project_demo.ui.fragment.wode.fragment.money;

import android.support.annotation.Nullable;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyBean.MoneyBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MyMoneyAdapter extends BaseQuickAdapter<MoneyBean.ResultBean.DetailListBean,BaseViewHolder> {
    public MyMoneyAdapter(int layoutResId, @Nullable List<MoneyBean.ResultBean.DetailListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MoneyBean.ResultBean.DetailListBean item) {
        helper.setText(R.id.money,item.getAmount()+"");
        helper.setText(R.id.time,item.getConsumerTime()+"");
    }
}
