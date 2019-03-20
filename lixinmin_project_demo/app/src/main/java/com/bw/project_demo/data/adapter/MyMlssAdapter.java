package com.bw.project_demo.data.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bw.project_demo.R;
import com.bw.project_demo.data.beans.ShopBeans;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMlssAdapter extends RecyclerView.Adapter<MyMlssAdapter.ViewHolder> {
    FragmentActivity context;
    List<ShopBeans.ResultBean.RxxpBean.CommodityListBean> rxxp;
    OnImgClickListen onImgClickListen;
    public interface OnImgClickListen{
        public void OnClick(int commodityId);
    }
    public void setOnImgClickListen(OnImgClickListen onImgClickListen){
        this.onImgClickListen=onImgClickListen;
    }
    public MyMlssAdapter(FragmentActivity context, List<ShopBeans.ResultBean.RxxpBean.CommodityListBean> rxxp) {
        this.context = context;
        this.rxxp = rxxp;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.mlss_item, null);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        RoundedCorners roundedCorners = new RoundedCorners(20);
        RequestOptions override = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Glide.with(context).load(rxxp.get(position).getMasterPic()).apply(override).into(holder.img);
       holder.tvPrice.setText("$"+rxxp.get(position).getPrice());
       holder.tvTitle.setText(rxxp.get(position).getCommodityName());

       holder.img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
//               int commodityId = rxxp.get(position).getCommodityId();
//               Toast.makeText(context, "commodityId:" + commodityId, Toast.LENGTH_SHORT).show();
               onImgClickListen.OnClick(rxxp.get(position).getCommodityId());
           }
       });
    }

    @Override
    public int getItemCount() {
        return rxxp.size();
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
