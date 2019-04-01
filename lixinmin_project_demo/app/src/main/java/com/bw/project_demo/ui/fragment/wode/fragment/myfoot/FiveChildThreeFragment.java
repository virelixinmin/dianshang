package com.bw.project_demo.ui.fragment.wode.fragment.myfoot;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.project_demo.R;
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FiveChildThreeFragment extends Fragment {
    @BindView(R.id.my_child_footer_ryl)
    RecyclerView myChildFooterRyl;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getActivity(), R.layout.five_child_three, null);

        SharedPreferences data = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        int userId = data.getInt("userId", 5);
        String sessionId = data.getString("sessionId", "15011445417");
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId + "");
        map.put("sessionId", sessionId);
        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
                .getFootResponseData(map,1,20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FootBean>() {
                    @Override
                    public void accept(FootBean footBean) throws Exception {
                        List<FootBean.ResultBean> result = footBean.getResult();
                        MyFootAdapter adapter = new MyFootAdapter(R.layout.foot_item, result);
                        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                        myChildFooterRyl.setLayoutManager(manager);
                        myChildFooterRyl.setAdapter(adapter);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
