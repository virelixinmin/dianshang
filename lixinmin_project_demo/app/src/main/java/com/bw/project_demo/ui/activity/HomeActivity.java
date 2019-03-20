package com.bw.project_demo.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.FirstFragment;
import com.bw.project_demo.ui.fragment.wode.FiveFragment;
import com.bw.project_demo.ui.fragment.dingdan.FourFragment;
import com.bw.project_demo.ui.fragment.ThreeFragment;
import com.bw.project_demo.ui.fragment.quanzi.TwoFragment;


import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.fr)
    FrameLayout fr;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.rb4)
    RadioButton rb4;
    @BindView(R.id.rb5)
    RadioButton rb5;
    @BindView(R.id.rg)
    RadioGroup rg;

    private FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //解决软键盘弹出时底部布局上移
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        manager = getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        tran.add(R.id.fr,new FirstFragment());
        tran.commit();



        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
            case R.id.rb1:
                manager.beginTransaction().replace(R.id.fr,new FirstFragment()).commit();
                break;
            case R.id.rb2:
                manager.beginTransaction().replace(R.id.fr,new TwoFragment()).commit();
                break;
            case R.id.rb3:
                manager.beginTransaction().replace(R.id.fr,new ThreeFragment()).commit();
                break;
            case R.id.rb4:
                manager.beginTransaction().replace(R.id.fr,new FourFragment()).commit();
                break;
            case R.id.rb5:
                manager.beginTransaction().replace(R.id.fr,new FiveFragment()).commit();
                break;
        }

            }
        });
//        int id = getIntent().getIntExtra("id", 0);
//        if (id==1){
//            rg.check(R.id.rb3);
//           rg.
//            //manager.beginTransaction().replace(R.id.fr,new ThreeFragment()).addToBackStack(null).commit();
//        }
//        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



    }


//

}
