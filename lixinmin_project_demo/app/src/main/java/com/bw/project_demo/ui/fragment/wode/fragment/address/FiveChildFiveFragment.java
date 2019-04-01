package com.bw.project_demo.ui.fragment.wode.fragment.address;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.project_demo.R;
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.ui.fragment.wode.fragment.address.Adapter.MyAddressAdapter;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressBeans.AddressBean;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressConstance.AddressContance;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressPresenter.AddressPresenterImpl;

import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.adapter.MyCircleAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class FiveChildFiveFragment extends Fragment implements AddressContance.AddressView {
    @BindView(R.id.my_child_address_complet)
    TextView myChildAddressComplet;
    @BindView(R.id.my_child_address_ryl)
    RecyclerView myChildAddressRyl;
    @BindView(R.id.my_child_address_addaddress)
    TextView myChildAddressAddaddress;
    Unbinder unbinder;
    private AddressContance.AddressPresenter presenter;
    private int userId;
    private String sessionId;
    private MyAddressAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getActivity(), R.layout.five_child_five, null);
        unbinder = ButterKnife.bind(this, inflate);

        SharedPreferences data = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        userId = data.getInt("userId", 184);
        sessionId = data.getString("sessionId", "15011445417");
        presenter = new AddressPresenterImpl();
        presenter.attahView(this);
        presenter.requestModel(userId, sessionId);



        myChildAddressComplet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return inflate;
    }



    @Override
    public void showData(final List<AddressBean.ResultBean> result) {
        adapter = new MyAddressAdapter(R.layout.address_list_item,result);
        myChildAddressRyl.setLayoutManager(new LinearLayoutManager(getActivity()));
        myChildAddressRyl.setAdapter(adapter);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, final View view, final int position) {
                final Button updata = view.findViewById(R.id.myaddress_item_updata);
                Button dele = view.findViewById(R.id.myaddress_item_delete);
                updata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(getActivity(),AddAddressActivity.class);
                        in.putExtra("name",result.get(position).getRealName());
                        in.putExtra("phone",result.get(position).getPhone());
                        in.putExtra("address",result.get(position).getAddress());
                        in.putExtra("youbian",result.get(position).getZipCode());
                        in.putExtra("id",result.get(position).getId());
                        in.putExtra("iss","0");
                        startActivity(in);



                    }
                });

            }
        });
        myChildAddressAddaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),AddAddressActivity.class);
                in.putExtra("iss","1");
                startActivity(in);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                //Toast.makeText(getActivity(), "result.get(position).getId():" + result.get(position).getId(), Toast.LENGTH_SHORT).show();
                TextView tv = view.findViewById(R.id.default_tv);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        HashMap<String, String> map = new HashMap<>();
                        map.put("userId",userId+"");
                        map.put("sessionId",sessionId);
                        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
                                .getDefaultData(map,result.get(position).getId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<ResponseBody>() {
                                    @Override
                                    public void accept(ResponseBody responseBody) throws Exception {
                                        String s = responseBody.string();
                                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {

                                    }
                                });
                    }
                });

            }
        });


    }



    @Override
    public void onResume() {
        super.onResume();
        presenter.requestModel(userId,sessionId);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
