package com.bw.project_demo.ui.fragment.wode.fragment.address;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressUpdate.UpdateAddressConstance.UpdateConstance;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressUpdate.UpdatePresenter.UpdatePresenterImpl;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAddressActivity extends AppCompatActivity implements UpdateConstance.UpdateView {

    @BindView(R.id.address_title_name)
    TextView addressTitleName;
    @BindView(R.id.address_new_name)
    com.xw.repo.XEditText addressNewName;
    @BindView(R.id.address_title_phone)
    TextView addressTitlePhone;
    @BindView(R.id.address_new_phone)
    com.xw.repo.XEditText addressNewPhone;
    @BindView(R.id.address_title_address)
    TextView addressTitleAddress;
    @BindView(R.id.address_new_address)
    com.xw.repo.XEditText addressNewAddress;
    @BindView(R.id.address_new_sj)
    TextView addressNewSj;
    @BindView(R.id.address_title_xx)
    TextView addressTitleXx;
    @BindView(R.id.address_new_xx)
    com.xw.repo.XEditText addressNewXx;
    @BindView(R.id.address_title_yb)
    TextView addressTitleYb;
    @BindView(R.id.address_new_zipcode)
    com.xw.repo.XEditText addressNewZipcode;
    @BindView(R.id.address_new_btn)
    Button addressNewBtn;
    private Intent intent;
    private int id;
    private UpdateConstance.UpdatePresenter presenter;
    private int userId;
    private String sessionId;
    private HashMap<String, String> map2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);


        presenter = new UpdatePresenterImpl();
        presenter.attachView(this);
        SharedPreferences data = getSharedPreferences("data", Context.MODE_PRIVATE);
        userId = data.getInt("userId", 184);
        sessionId = data.getString("sessionId", "15011445417");
        map2 = new HashMap<>();
        map2.put("userId",userId+"");
        map2.put("sessionId",sessionId);
        intent = getIntent();
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String address = intent.getStringExtra("address");
        String youbian = intent.getStringExtra("youbian");
        id = intent.getIntExtra("id",5);
        String iss = intent.getStringExtra("iss");
        if (iss.equals("0")){
            addressNewName.setTextEx(name);
            addressNewPhone.setTextEx(phone);
            addressNewAddress.setTextEx(address);
            addressNewZipcode.setTextEx(youbian);

        }

        addressNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String iss = intent.getStringExtra("iss");

                if (iss.equals("0")){
                    map2 = new HashMap<>();
                    map2.put("userId",userId+"");
                    map2.put("sessionId",sessionId);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("id",id+"");
                    map.put("realName",addressNewName.getTextEx().toString());
                    map.put("phone",addressNewPhone.getTextEx().toString());
                    map.put("address",addressNewAddress.getTextEx().toString());
                    map.put("zipCode",addressNewZipcode.getTextEx().toString());
                    presenter.requestUpdateModel(map2,map);



                }else if (iss.equals("1")){
                    map2 = new HashMap<>();
                    map2.put("userId",userId+"");
                    map2.put("sessionId",sessionId);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("realName",addressNewName.getTextEx().toString());
                    map.put("phone",addressNewPhone.getTextEx().toString());
                    map.put("address",addressNewAddress.getTextEx().toString());
                    map.put("zipCode",addressNewZipcode.getTextEx().toString());
                    presenter.requestAddModel(map2,map);
                }

            }
        });

    }

    @Override
    public void showUpdateData(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showAddData(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

    }
}
