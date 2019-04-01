package com.bw.project_demo.data.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bw.project_demo.R;
import com.bw.project_demo.data.beans.ShopBeans;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//首页时尚生活的适配器
public class MyFashionAdapter extends RecyclerView.Adapter<MyFashionAdapter.ViewHolder> {
    FragmentActivity context;
    List<ShopBeans.ResultBean.MlssBean.CommodityListBeanXX> fashion;
    OnfashionImgClickListen onfashionImgClickListen;
    //时尚生活的接口回调
    public interface OnfashionImgClickListen{
        public void OnClick(int commodityId);
    }
    public void setOnImgClickListen(OnfashionImgClickListen onfashionImgClickListen){
        this.onfashionImgClickListen=onfashionImgClickListen;
    }
    public MyFashionAdapter(FragmentActivity context, List<ShopBeans.ResultBean.MlssBean.CommodityListBeanXX> fashion) {
        this.context = context;
        this.fashion=fashion;

    }
    //绑定布局和viewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.fash_item, null);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }
    //给控件赋值
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        RoundedCorners roundedCorners = new RoundedCorners(20);
        RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Glide.with(context).load(fashion.get(position).getMasterPic()).apply(override).into(holder.img);
       holder.tvPrice.setText("$"+fashion.get(position).getPrice());
       holder.tvTitle.setText(fashion.get(position).getCommodityName());
       //给文件设置接口回调 ui界面将 得到的id回传
       holder.img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onfashionImgClickListen.OnClick(fashion.get(position).getCommodityId());
           }
       });
       holder.tvTitle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onfashionImgClickListen.OnClick(fashion.get(position).getCommodityId());
           }
       });
    }

    @Override
    public int getItemCount() {
        return fashion.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
