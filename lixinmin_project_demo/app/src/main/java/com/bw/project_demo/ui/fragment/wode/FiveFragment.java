package com.bw.project_demo.ui.fragment.wode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.project_demo.R;
import com.bw.project_demo.ui.activity.MainActivity;
import com.bw.project_demo.ui.fragment.wode.fragment.person.PersonBean;
import com.bw.project_demo.ui.fragment.wode.fragment.person.PersonConstance.PersonConstance;
import com.bw.project_demo.ui.fragment.wode.fragment.person.PersonPresenter.PersonPresenterImpl;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FiveFragment extends Fragment implements PersonConstance.PersonView {
    @BindView(R.id.my_touxiang)
    ImageView myTouxiang;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.my_name)
    TextView myName;
    @BindView(R.id.my_information)
    TextView myInformation;
    @BindView(R.id.my_circle)
    TextView myCircle;
    @BindView(R.id.my_foot)
    TextView myFoot;
    @BindView(R.id.my_wallet)
    TextView myWallet;
    @BindView(R.id.my_address)
    TextView myAddress;
    Unbinder unbinder;
    @BindView(R.id.tuichu)
    Button tuichu;
    private FragmentManager manager;
    private PersonConstance.PersonPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.five_item, null);
        unbinder = ButterKnife.bind(this, v);
        SharedPreferences data = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        int userId = data.getInt("userId", 5);
        String sessionId = data.getString("sessionId", "150114445417");
        presenter = new PersonPresenterImpl();

        presenter.attagView(this);
        presenter.requestModel(userId, sessionId);
        myTouxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.cature, null);
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setView(view);
                TextView xiangce = view.findViewById(R.id.xiangce);
                TextView paizhao = view.findViewById(R.id.paizhao);

                xiangce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_GET_CONTENT);
                        i.setType("image/*");
                        startActivityForResult(i, 100);
                    }
                });
                paizhao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(in, 111);
                    }
                });
                alertDialog.show();

            }
        });
        tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences data1 = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = data1.edit();
                edit.clear();
                edit.commit();
                startActivity(new Intent(getActivity(),MainActivity.class));
            }
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {

            try {
                Uri data1 = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data1);
                myTouxiang.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.my_touxiang, R.id.my_name, R.id.my_information, R.id.my_circle, R.id.my_foot, R.id.my_wallet, R.id.my_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_touxiang:
                break;
            case R.id.my_name:
                break;
            case R.id.my_information:
                Intent in = new Intent(getActivity(), HubActivity.class);
                in.putExtra("id", 1);
                startActivity(in);
                break;
            case R.id.my_circle:
                Intent inn = new Intent(getActivity(), HubActivity.class);
                inn.putExtra("id", 2);
                startActivity(inn);
                break;
            case R.id.my_foot:
                Intent innn = new Intent(getActivity(), HubActivity.class);
                innn.putExtra("id", 3);
                startActivity(innn);
                break;
            case R.id.my_wallet:
                Intent innnn = new Intent(getActivity(), HubActivity.class);
                innnn.putExtra("id", 4);
                startActivity(innnn);
                break;
            case R.id.my_address:
                Intent innnnn = new Intent(getActivity(), HubActivity.class);
                innnnn.putExtra("id", 5);
                startActivity(innnnn);
                break;
        }
    }

    @Override
    public void showData(PersonBean personBean) {
        Glide.with(getActivity()).load(personBean.getResult().getHeadPic()).into(myTouxiang);
        myName.setText(personBean.getResult().getNickName());
    }
}
