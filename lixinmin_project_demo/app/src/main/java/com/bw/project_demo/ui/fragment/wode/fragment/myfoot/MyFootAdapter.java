package com.bw.project_demo.ui.fragment.wode.fragment.myfoot;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.project_demo.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MyFootAdapter extends BaseQuickAdapter<FootBean.ResultBean,BaseViewHolder> {
    public MyFootAdapter(int layoutResId, @Nullable List<FootBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FootBean.ResultBean item) {
        helper.setText(R.id.tv_title,item.getCommodityName());
        helper.setText(R.id.tv_price,"$"+item.getPrice());
        Glide.with(mContext).load(item.getMasterPic()).into((ImageView) helper.getView(R.id.foot_img));
    }
}
