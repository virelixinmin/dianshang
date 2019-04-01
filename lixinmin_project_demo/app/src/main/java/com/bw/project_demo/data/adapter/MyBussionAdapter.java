package com.bw.project_demo.data.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.project_demo.R;
import com.bw.project_demo.data.beans.FindShoppingBean;
import com.bw.project_demo.data.beans.ShoppingBeans;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
    //这是商品的适配器
    public class MyBussionAdapter extends BaseQuickAdapter<FindShoppingBean.ResultBean,BaseViewHolder> {


        //    //这是加减法的接口回调
//    AdditionSetOnClick additionClickListen;
//    public interface AdditionSetOnClick{
//        public void jia(int i);
//        public void jian(int i);
//    }
//    public void setOnAdditionClickListen(AdditionSetOnClick additionClickListen){
//        this.additionClickListen=additionClickListen;
//    }
//
    BussionCallBack bussionCallBack;
    private TextView num;
    //接口回调方法
    public interface BussionCallBack{
        public void onCallBack();
    }
    public void setOnClickListen(BussionCallBack bussionCallBack){
        this.bussionCallBack=bussionCallBack;
    }
    public MyBussionAdapter(int layoutResId, @Nullable List<FindShoppingBean.ResultBean> data) {
            super(layoutResId, data);
    }
    //给数据的配置值
    @Override
    protected void convert(BaseViewHolder helper, final FindShoppingBean.ResultBean item) {
       helper.setText(R.id.tv_num,item.getCount()+"");
        helper.setText(R.id.tv_price,"$"+item.getPrice());
        helper.setText(R.id.tv_name,item.getCommodityName());
        Glide.with(mContext).load(item.getPic()).into((ImageView) helper.getView(R.id.img_shopping));
        final CheckBox cb_goods = helper.getView(R.id.cb_goods);

        cb_goods.setOnCheckedChangeListener(null);
        cb_goods.setChecked(item.getChecked());

        final int position = helper.getPosition();
    //点击商品选中的状态
        cb_goods.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              item.setChecked(cb_goods.isChecked());
              notifyDataSetChanged();
              bussionCallBack.onCallBack();
          }

      });

        num = helper.getView(R.id.tv_num);
        //获取加减器的id
        ImageButton img_jia = helper.getView(R.id.jia);
        ImageButton img_jian = helper.getView(R.id.jian);
        img_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int saleNum = item.getCount();
                int i=saleNum+1;
                item.setCount(i);
                notifyDataSetChanged();
                bussionCallBack.onCallBack();

            }
        });
        //给点击事件
        img_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int saleNum = item.getCount();
                int i = saleNum-1;
                if (i < 0){
                    i=0;
                    item.setCount(i);
                }
                item.setCount(i);
                notifyDataSetChanged();
                bussionCallBack.onCallBack();


            }
        });
//        setOnAdditionClickListen(new AdditionSetOnClick() {
//            @Override
//            public void jia(int i) {
//                num.setText(i+"");
//                notifyDataSetChanged();
//            }
//
//            @Override
//            public void jian(int i) {
//                num.setText(i+"");
//            }
//        });

    }



}

