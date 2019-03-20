package com.bw.project_demo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bw.project_demo.R;
import com.bw.project_demo.data.beans.LoginBean;
import com.bw.project_demo.di.Contract.LoginContractAll;
import com.bw.project_demo.di.presenter.LoginPresenterImpl;
import com.google.gson.Gson;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements LoginContractAll.LoginView {

    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.remember_check)
    CheckBox rememberCheck;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.Login_btn)
    Button LoginBtn;
    @BindView(R.id.lr)
    LinearLayout lr;
    @BindView(R.id.togg_login_btn)
    ToggleButton toggLoginBtn;
    private LoginContractAll.LoginPresenter presenter;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

         presenter = new LoginPresenterImpl();
         presenter.attahView(this);
        Intent intent = getIntent();

        String phone = intent.getStringExtra("phone");
        String pwd = intent.getStringExtra("pwd");
        editPhone.setText(phone);
        editPwd.setText(pwd);

        toggLoginBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    editPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

         sp = getSharedPreferences("data", Context.MODE_PRIVATE);
         edit = sp.edit();

        //判断是否点击记住密码
        if (sp.getBoolean("flag",false)==true){
            editPhone.setText(sp.getString("phone",""));
            editPwd.setText(sp.getString("pwd",""));
            rememberCheck.setChecked(true);
        }

        //判断是否是第一次登录
        if (sp.getBoolean("zidong",false)==true){
            this.finish();
            presenter.requestModel(sp.getString("phone","15011445417"),sp.getString("pwd","123"));
        }


        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);



    }





    @OnClick({R.id.remember_check, R.id.Login_btn, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
            case R.id.remember_check:
                break;
            case R.id.Login_btn:
               String editphone = editPhone.getText().toString();
              String editpwd = editPwd.getText().toString();
              presenter.requestModel(editphone,editpwd);
                if (rememberCheck.isChecked()){
                    edit.putString("phone",editPhone.getText().toString());
                    edit.putString("pwd",editPwd.getText().toString());
                    edit.putBoolean("flag",true);
                    edit.putBoolean("zidong",true);
                }else{
                    edit.clear();
                }
                edit.commit();


                break;
        }
    }


    @Override
    public void ShowData(String result) {

            Gson g= new Gson();
            LoginBean loginBean = g.fromJson(result, LoginBean.class);
            String message = loginBean.getMessage();

//            Object userId = result.get("userId");
//            Toast.makeText(this, "userId:" + userId, Toast.LENGTH_SHORT).show();

            if (message.equals("登录成功")){
                startActivity(new Intent(MainActivity.this,HomeActivity.class));

                finish();
            }else{
                Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
            }
        int userId = loginBean.getResult().getUserId();
        String sessionId = loginBean.getResult().getSessionId();
        edit.putInt("userId",userId);
        edit.putString("sessionId",sessionId);
        edit.commit();




    }
}
