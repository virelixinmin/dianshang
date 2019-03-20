package com.bw.project_demo.ui.fragment.quanzi.Adapter;

import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.quanzi.bean.CircleBeans;
import com.bw.project_demo.ui.fragment.quanzi.utils.JiKeLikeView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Arrays;
import java.util.List;

public class MyCircleAdapter extends BaseQuickAdapter<CircleBeans.ResultBean,BaseViewHolder> {


    private RecyclerView my_rcy;
    List<CircleBeans.ResultBean> result;
    OnItemHandClickListner onItemHandClickListner;
    private ImageView my_circle_childimage_hand;
    private MyChildImageAdapter myCircleImageAdapter;

    public void setOnItemHandClickListner(OnItemHandClickListner onItemHandClickListner1){
        this.onItemHandClickListner=onItemHandClickListner1;
    }
    public interface OnItemHandClickListner{
        void click(int id,int whetherGreatId);
    }
//    OnItemHandLight OnItemHandLight;
//    public void setOnItemhHhHhH(OnItemHandLight onItemHandLight){
//        this.OnItemHandLight=onItemHandLight;
//
//    }
//    public interface OnItemHandLight{
//        void click(List<CircleBeans.ResultBean> greatNum);
//    }

    public MyCircleAdapter(int circle_item, List<CircleBeans.ResultBean> result) {
        super(circle_item,result);
        this.result=result;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CircleBeans.ResultBean item) {
        helper.setText(R.id.my_circle_name,item.getNickName());
        Uri parse = Uri.parse(item.getHeadPic());
        SimpleDraweeView drwee = helper.getView(R.id.my_circle_icon);
        drwee.setImageURI(parse);
        helper.setText(R.id.my_circle_content,item.getContent());
        my_rcy = helper.getView(R.id.my_circle_childimage_ryl);
        int greatNum = item.getGreatNum();
//        OnItemHandLight.click(result);
        //获取赞的状态
//        int whetherGreat = item.getWhetherGreat();
//        if (whetherGreat==2){
//            my_circle_childimage_hand = helper.getView(R.id.my_circle_childimage_hand);
//            my_circle_childimage_hand.setImageResource(R.mipmap.common_btn_prise_n_xxhdpi);
//        }else{
//             my_circle_childimage_hand = helper.getView(R.id.my_circle_childimage_hand);
//            my_circle_childimage_hand.setImageResource(R.mipmap.common_btn_prise_s_xxhdpi);
//        }





        if(item.getImage()==null){
            my_rcy.setVisibility(View.GONE);
        } else {
            my_rcy.setVisibility(View.VISIBLE);
            String[] images = item.getImage().split(",");
            int imageCount = images.length;
            int colNum;//列数

            if (imageCount == 1) {
                colNum = 1;
            } else if (imageCount == 2 || imageCount == 4) {
                colNum = 2;
            } else {
                colNum = 3;
            }
            myCircleImageAdapter = new MyChildImageAdapter(images, mContext);
            my_rcy.setLayoutManager(new GridLayoutManager(mContext,colNum));
            my_rcy.setAdapter(myCircleImageAdapter);
        }
        myCircleImageAdapter.notifyDataSetChanged();

//        my_circle_childimage_greatenum.setText(item.getGreatNum()+"");

//        my_circle_childimage_hand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemHandClickListner.click(item.getId(),item.getWhetherGreat());
//            }
//        });
//        MyChildImageAdapter adapter = new MyChildImageAdapter(R.layout.my_child_image_item, result);
//        LinearLayoutManager manager = new LinearLayoutManager(mContext);
//        my_rcy.setLayoutManager(manager);
//        my_rcy.setAdapter(adapter);

    }
}
