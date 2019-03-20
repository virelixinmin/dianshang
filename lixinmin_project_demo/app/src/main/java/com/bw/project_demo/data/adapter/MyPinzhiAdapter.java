package com.bw.project_demo.data.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.project_demo.R;
import com.bw.project_demo.data.beans.ShopBeans;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyPinzhiAdapter extends RecyclerView.Adapter<MyPinzhiAdapter.ViewHolder> {
    FragmentActivity context;
    List<ShopBeans.ResultBean.PzshBean.CommodityListBeanX> pinzhi;
    OnpinzhiImgClickListen onpinzhiImgClickListen;
    public interface OnpinzhiImgClickListen{
        public void OnClick(int commodityId);
    }
    public void setOnImgClickListen(OnpinzhiImgClickListen onpinzhiImgClickListen){
        this.onpinzhiImgClickListen=onpinzhiImgClickListen;
    }
    public MyPinzhiAdapter(FragmentActivity context, List<ShopBeans.ResultBean.PzshBean.CommodityListBeanX> pinzhi) {
        this.context = context;
        this.pinzhi=pinzhi;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.pinzhi_item, null);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context).load(pinzhi.get(position).getMasterPic()).into(holder.img);
       holder.tvPrice.setText("$"+pinzhi.get(position).getPrice());
       holder.tvTitle.setText(pinzhi.get(position).getCommodityName());
       holder.img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              onpinzhiImgClickListen.OnClick(pinzhi.get(position).getCommodityId());
           }
       });
    }

    @Override
    public int getItemCount() {
        return pinzhi.size();
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
