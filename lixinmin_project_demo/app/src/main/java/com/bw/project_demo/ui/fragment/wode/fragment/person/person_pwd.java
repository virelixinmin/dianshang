package com.bw.project_demo.ui.fragment.wode.fragment.person;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.wode.fragment.person.ServiceApp.PersonServiceApp;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class person_pwd extends AppCompatActivity {

    @BindView(R.id.update_name)
    TextView updateName;
    @BindView(R.id.edit_name_pwd)
    EditText editNamePwd;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.btn_pwd_save)
    Button btnPwdSave;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_pwd);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        pwd = intent.getStringExtra("pwd");

        SharedPreferences data = getSharedPreferences("data", Context.MODE_PRIVATE);
        final int userId = data.getInt("userId", 5);
        final String sessionId = data.getString("sessionId", "123");
        btnPwdSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit build = new Retrofit.Builder()
                        .baseUrl(constanccc.urlPersonString)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                HashMap<String, String> map = new HashMap<>();
                map.put("userId",userId+"");
                map.put("sessionId",sessionId);
                PersonServiceApp personServiceApp = build.create(PersonServiceApp.class);
                Observable<ResponseBody> updatePwdData = personServiceApp.getUpdatePwdData(map, editNamePwd.getText().toString(), editPwd.getText().toString());
                updatePwdData.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
                                String s = responseBody.string().toString();
                                Toast.makeText(person_pwd.this, s, Toast.LENGTH_SHORT).show();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        });
            }
        });

    }
}
