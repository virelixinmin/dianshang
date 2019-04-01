package com.bw.project_demo.ui.fragment.wode.fragment.mycircle.adapter;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.project_demo.R;
import com.bw.project_demo.data.utils.TimeUtils;
import com.bw.project_demo.ui.fragment.quanzi.Adapter.MyChildImageAdapter;
import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.beans.circlebean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyCircleAdapter extends BaseQuickAdapter<circlebean.ResultBean,BaseViewHolder> {
    private ImageView my_circle_childimage_hand;
    //表示未选中的CheckBox
    private static final int NOSST_CHECK=-1;
    //是否处于多选状态
    private boolean isMusltSelect=false;
    ImageView myGroupDelete;
    List<circlebean.ResultBean> data;
    public MyCircleAdapter(int layoutResId, @Nullable List<circlebean.ResultBean> data, ImageView myGroupDelete) {
        super(layoutResId, data);
        this.myGroupDelete=myGroupDelete;
        this.data=data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final circlebean.ResultBean item) {

        helper.setText(R.id.my_circle_name,item.getNickName());
        Uri parse = Uri.parse(item.getHeadPic());
        SimpleDraweeView drwee = helper.getView(R.id.my_circle_icon_mine);
        long createTime = item.getCreateTime();
        String s = TimeUtils.longToDate(createTime);
        helper.setText(R.id.my_circle_time,s);
        drwee.setImageURI(parse);
        helper.setText(R.id.my_circle_content,item.getContent());
        int whetherGreat = item.getGreatNum();
        String image = item.getImage();
        int i1 = image.indexOf(",");
        RecyclerView my_rcy = helper.getView(R.id.my_circle_childimage_ryl);
        if (i1==-1) {
            String[] images={image};
            MyChildImageAdapter myCircleImageAdapter = new MyChildImageAdapter(images, mContext);
            my_rcy.setLayoutManager(new GridLayoutManager(mContext,3));
            my_rcy.setAdapter(myCircleImageAdapter);
        }
        else {
            String[] strings = image.split(",");
            MyChildImageAdapter myCircleImageAdapter = new MyChildImageAdapter(strings, mContext);
            my_rcy.setLayoutManager(new GridLayoutManager(mContext,3));
            my_rcy.setAdapter(myCircleImageAdapter);
        }

//        if (whetherGreat==2){
//            my_circle_childimage_hand = helper.getView(R.id.my_circle_childimage_hand);
//            my_circle_childimage_hand.setImageResource(R.mipmap.common_btn_prise_n_xxhdpi);
//        }else{
//            my_circle_childimage_hand = helper.getView(R.id.my_circle_childimage_hand);
//            my_circle_childimage_hand.setImageResource(R.mipmap.common_btn_prise_s_xxhdpi);
//        }
        final CheckBox checkBox = helper.getView(R.id.chebox);
        int position = helper.getPosition();
        helper.addOnClickListener(R.id.chebox);
//        helper.setVisible(R.id.chebox,isMusltSelect);
//        myGroupDelete.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//
//                   if (!isMusltSelect){
//                       isMusltSelect=true;
//                       helper.setVisible(R.id.chebox,isMusltSelect);
//
//                   }else{
//                       isMusltSelect=false;
//                       helper.setVisible(R.id.chebox,isMusltSelect);
//                   }
//
//
//           }
//       });


    }
    MyCircleCallBack myCircleCallBack;
    public interface MyCircleCallBack{
        public void getData(CheckBox checkBox);
    }
    public void setOnMyCircleCallBack(MyCircleCallBack myCircleCallBack){
        this.myCircleCallBack=myCircleCallBack;
    }
}
