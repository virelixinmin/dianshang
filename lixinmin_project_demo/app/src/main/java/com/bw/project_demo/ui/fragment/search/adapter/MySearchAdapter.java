package com.bw.project_demo.ui.fragment.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.search.SearchActivity;
import com.bw.project_demo.ui.fragment.search.SearchBeans;
import com.bw.project_demo.ui.fragment.xiangqing.DetailsBeans.beans;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MySearchAdapter extends RecyclerView.Adapter<MySearchAdapter.ViewHolder> {
    SearchActivity content;
    List<SearchBeans.ResultBean> result;



    public MySearchAdapter(SearchActivity content, List<SearchBeans.ResultBean> result) {
        this.content = content;
        this.result = result;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(content, R.layout.mlss_item, null);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(content).load(result.get(position).getMasterPic()).into(holder.img);
        holder.tvTitle.setText(result.get(position).getCommodityName());
        holder.tvPrice.setText(result.get(position).getPrice()+"");
    }

    @Override
    public int getItemCount() {
        return result.size();
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
