package com.bw.project_demo.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.search.SearchActivity;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.core.AttachPopupView;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.impl.AttachListPopupView;
import com.lxj.xpopup.impl.PartShadowPopupView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Xpopuwindow extends CenterPopupView {

    @BindView(R.id.men_yi)
    TextView menYi;
    @BindView(R.id.women_yi)
    TextView womenYi;
    @BindView(R.id.women_xie)
    TextView womenXie;
    @BindView(R.id.men_txue)
    TextView menTxue;
    @BindView(R.id.meifu)
    TextView meifu;
    @BindView(R.id.shuma)
    TextView shuma;
    Context context;
    public Xpopuwindow(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_popup;
    }

    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑



    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
       menYi.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent in = new Intent(context,SearchActivity.class);
               in.putExtra("search_name","男装");
               context.startActivity(in);
           }
       });
        womenYi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context,SearchActivity.class);
                in.putExtra("search_name","女装");
                context.startActivity(in);
            }
        });
        womenXie.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context,SearchActivity.class);
                in.putExtra("search_name","女鞋");
                context.startActivity(in);
            }
        });
        menTxue.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context,SearchActivity.class);
                in.putExtra("search_name","T恤");
                context.startActivity(in);
            }
        });
        meifu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context,SearchActivity.class);
                in.putExtra("search_name","美肤妆品");
                context.startActivity(in);
            }
        });

        shuma.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context,SearchActivity.class);
                in.putExtra("search_name","手机数据");
                context.startActivity(in);
            }
        });


    }

    // 设置最大宽度，看需要而定
    @Override
    protected int getMaxWidth() {
        return super.getMaxWidth();
    }

    // 设置最大高度，看需要而定
    @Override
    protected int getMaxHeight() {
        return super.getMaxHeight();
    }

    // 设置自定义动画器，看需要而定
    @Override
    protected PopupAnimator getPopupAnimator() {
        return super.getPopupAnimator();
    }

}
