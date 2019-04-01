package com.bw.project_demo.ui.fragment.dingdan.fragment.payment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.project_demo.R;
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayMentActivity extends AppCompatActivity {

    @BindView(R.id.money_title)
    TextView moneyTitle;
    @BindView(R.id.image_money)
    ImageView imageMoney;
    @BindView(R.id.image_weixin)
    ImageView imageWeixin;
    @BindView(R.id.image_zhifubao)
    ImageView imageZhifubao;
    @BindView(R.id.radio_yue)
    RadioButton radioYue;
    @BindView(R.id.radio_wei)
    RadioButton radioWei;
    @BindView(R.id.radio_zhi)
    RadioButton radioZhi;
    @BindView(R.id.money_pay)
    Button moneyPay;
    @BindView(R.id.success_image)
    ImageView successImage;
    @BindView(R.id.success_text)
    TextView successText;
    @BindView(R.id.success_back)
    Button successBack;
    @BindView(R.id.pay_success)
    RelativeLayout paySuccess;
    @BindView(R.id.fail_image)
    ImageView failImage;
    @BindView(R.id.fail_text)
    TextView failText;
    @BindView(R.id.fail_back)
    TextView failBack;
    @BindView(R.id.fail_pay)
    Button failPay;
    @BindView(R.id.pay_fail)
    RelativeLayout payFail;
    private int userId;
    private String sessionId;
    private String ord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ment);
        ButterKnife.bind(this);

        SharedPreferences data = PayMentActivity.this.getSharedPreferences("data", Context.MODE_PRIVATE);
        userId = data.getInt("userId", 5);
        sessionId = data.getString("sessionId", "15011445417");

        Intent intent = getIntent();
        ord = intent.getStringExtra("ord");
    }

    @OnClick({R.id.radio_yue, R.id.radio_wei, R.id.radio_zhi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_yue:
                moneyPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("userId", userId + "");
                        map.put("sessionId", sessionId);
                        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class).getPayMent(map, ord,1)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<paybean>() {
                                    @Override
                                    public void accept(paybean paybean) throws Exception {
                                        if (paybean.getStatus().equals("0000")){
                                            paySuccess.setVisibility(View.VISIBLE);
                                        }else if (paybean.getStatus().equals("1001")){
                                            payFail.setVisibility(View.VISIBLE);
                                        }
                                        failPay.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                payFail.setVisibility(View.INVISIBLE);
                                            }
                                        });
                                        successBack.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                finish();
                                            }
                                        });


                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        String message = throwable.getMessage();
                                        Toast.makeText(PayMentActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

                break;
            case R.id.radio_wei:
                moneyPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payFail.setVisibility(View.VISIBLE);
                    }
                });

                failPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payFail.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case R.id.radio_zhi:
                moneyPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payFail.setVisibility(View.VISIBLE);
                    }
                });

                failPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payFail.setVisibility(View.INVISIBLE);
                    }
                });
                break;
        }
    }
}
