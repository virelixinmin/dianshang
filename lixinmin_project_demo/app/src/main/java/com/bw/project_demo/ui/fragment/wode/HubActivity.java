package com.bw.project_demo.ui.fragment.wode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.wode.fragment.address.FiveChildFiveFragment;
import com.bw.project_demo.ui.fragment.wode.fragment.money.FiveChildFourFragment;
import com.bw.project_demo.ui.fragment.wode.fragment.FiveChildFragment;
import com.bw.project_demo.ui.fragment.wode.fragment.myfoot.FiveChildThreeFragment;
import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.FiveChildTwoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HubActivity extends AppCompatActivity {

    @BindView(R.id.fr)
    FrameLayout fr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        ButterKnife.bind(this);
        FragmentManager ma = getSupportFragmentManager();
        FragmentTransaction tran = ma.beginTransaction();
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 1);
        if (id==1){
            tran.add(R.id.fr,new FiveChildFragment()).commit();
        }else if (id==2){
            tran.add(R.id.fr,new FiveChildTwoFragment()).commit();
        }else if (id==3){
            tran.add(R.id.fr,new FiveChildThreeFragment()).commit();
        }else if(id==4){
            tran.add(R.id.fr,new FiveChildFourFragment()).commit();
        }else{
            tran.add(R.id.fr,new FiveChildFiveFragment()).commit();
        }


    }
}
