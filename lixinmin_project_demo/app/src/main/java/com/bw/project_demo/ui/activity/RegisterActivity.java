package com.bw.project_demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bw.project_demo.R;
import com.bw.project_demo.di.Contract.ContractAll;
import com.bw.project_demo.di.presenter.ContractPresenterImpl;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements ContractAll.ContractView {

    @BindView(R.id.edit_register_phone)
    EditText editRegisterPhone;
    @BindView(R.id.verification)
    EditText verification;
    @BindView(R.id.edit_Register_pwd)
    EditText editRegisterPwd;
    @BindView(R.id.queck_login)
    TextView queckLogin;
    @BindView(R.id.Register_btn)
    Button RegisterBtn;
    @BindView(R.id.togglebtn)
    ToggleButton togglebtn;
    @BindView(R.id.huoqu)
    TextView huoqu;
    private ContractAll.ContractPresenter presenter;
    private TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        presenter = new ContractPresenterImpl();
        presenter.attchView(this);


        togglebtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editRegisterPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    editRegisterPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        timeCount = new TimeCount(60000,1000);
        huoqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeCount.start();
            }
        });
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


    }
//    //判断是否符合手机号标准
//    public  boolean isMobileNO(EditText mobiles){
//        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//        Matcher m = p.matcher((CharSequence) mobiles);
//
//        return m.matches();
//    }



        class TimeCount extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);


        }

        @Override
        public void onTick(long millisUntilFinished) {
            huoqu.setEnabled(false);
            huoqu.setText(millisUntilFinished/1000+"秒");
        }

        @Override
        public void onFinish() {
            huoqu.setEnabled(true);
            huoqu.setText("重新获取");
        }
    }

    @Override
    public void ShowData(Object s) {
            if(s.equals("注册成功")){
                Intent in = new Intent(RegisterActivity.this,MainActivity.class);
                in.putExtra("phone",editRegisterPhone.getText().toString());
                in.putExtra("pwd",editRegisterPwd.getText().toString());

                startActivity(in);
                finish();
            }else{
                Toast.makeText(this, "该账户已经注册", Toast.LENGTH_SHORT).show();
            }



    }

    @OnClick({R.id.queck_login, R.id.Register_btn, R.id.togglebtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.queck_login:
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
                timeCount.cancel();
                timeCount.onFinish();
                break;
            case R.id.Register_btn:
                String registerphone = editRegisterPhone.getText().toString();
                String registerpwd = editRegisterPwd.getText().toString();
                presenter.requestModel(registerphone, registerpwd);
                timeCount.cancel();
                timeCount.onFinish();

//                boolean mobileNO = isMobileNO(editRegisterPhone);
//                if (mobileNO){
//                    Toast.makeText(this, "请输入正确的手机格式", Toast.LENGTH_SHORT).show();
//                }
                break;

        }
    }


}
