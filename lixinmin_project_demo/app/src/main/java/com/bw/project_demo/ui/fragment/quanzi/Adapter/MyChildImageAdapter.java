package com.bw.project_demo.ui.fragment.quanzi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.quanzi.ShowActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyChildImageAdapter extends RecyclerView.Adapter<MyChildImageAdapter.ViewHolder> {
    String[] images;
    Context mContext;


    public MyChildImageAdapter(String[] images, Context mContext) {
        this.images = images;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.my_child_image_item, parent, false);
        ViewHolder holder = new ViewHolder(inflate);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String image = images[position];
        Uri parse = Uri.parse(image);
        holder.drweeImg.setImageURI(parse);

        holder.drweeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),ShowActivity.class);
                ArrayList<String> li =  new ArrayList<>();
                List<String> list = Arrays.asList(images);
                li.addAll(list);
                intent.putStringArrayListExtra("list", li);

                intent.putExtra("currentPosition",position);

                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.drwee_img)
        SimpleDraweeView drweeImg;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


//        String image = item.getImage();
//        int i = image.indexOf(",");
//
//        int position = helper.getPosition();
//        String s = split[position];
//        if (null!=s){
//            Uri parse = Uri.parse(s);
//            SimpleDraweeView drwee = helper.getView(R.id.drwee_img);
//            drwee.setImageURI(parse);
//        }


}
