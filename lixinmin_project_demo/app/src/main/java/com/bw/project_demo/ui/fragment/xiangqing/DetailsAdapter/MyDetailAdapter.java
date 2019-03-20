package com.bw.project_demo.ui.fragment.xiangqing.DetailsAdapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.xiangqing.Details.DetailsActivity;
import com.bw.project_demo.ui.fragment.xiangqing.DetailsBeans.beans;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyDetailAdapter extends RecyclerView.Adapter<MyDetailAdapter.ViewHolder> {
    DetailsActivity detailsActivity;
    List<beans> list;



    public MyDetailAdapter(DetailsActivity detailsActivity, List<beans> list) {
        this.detailsActivity = detailsActivity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(detailsActivity, R.layout.details_item, null);
        ViewHolder holder = new ViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String picture = list.get(position).getResult().getPicture();
        String[] split = picture.split(",");
        String s = split[position];
        Uri parse = Uri.parse(s);
        holder.goodsContentPageViewPager.setImageURI(parse);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goodsContentPage_ViewPager)
        SimpleDraweeView goodsContentPageViewPager;
        @BindView(R.id.goodsContent_goods_Price)
        TextView goodsContentGoodsPrice;
        @BindView(R.id.goodsContent_goods_yiShou)
        TextView goodsContentGoodsYiShou;
        @BindView(R.id.goodsContent_goods_title)
        TextView goodsContentGoodsTitle;
        @BindView(R.id.goodsContent_goods_weight)
        TextView goodsContentGoodsWeight;
        @BindView(R.id.goodsContent_product_contentImg)
        ImageView goodsContentProductContentImg;
        @BindView(R.id.goodsContent_product_introductionText)
        TextView goodsContentProductIntroductionText;
        @BindView(R.id.goodsContent_product_introductionImg)
        ImageView goodsContentProductIntroductionImg;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
