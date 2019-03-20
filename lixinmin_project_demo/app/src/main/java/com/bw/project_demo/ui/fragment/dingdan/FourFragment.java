package com.bw.project_demo.ui.fragment.dingdan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.dingdan.fragment.All_orders.All_orders;
import com.bw.project_demo.ui.fragment.dingdan.fragment.finished;
import com.bw.project_demo.ui.fragment.dingdan.fragment.obligation;
import com.bw.project_demo.ui.fragment.dingdan.fragment.receiving.receiving;
import com.bw.project_demo.ui.fragment.dingdan.fragment.remain;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FourFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.list_all_list)
    TextView listAllList;
    @BindView(R.id.list_wait_for_pay)
    TextView listWaitForPay;
    @BindView(R.id.list_wait_for_receive)
    TextView listWaitForReceive;
    @BindView(R.id.list_wait_for_evaluate)
    TextView listWaitForEvaluate;
    @BindView(R.id.list_completed)
    TextView listCompleted;
    @BindView(R.id.list_framelayout)
    FrameLayout listFramelayout;
    private FragmentManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.four_item, null);
        unbinder = ButterKnife.bind(this, v);
        manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().add(R.id.list_framelayout,new All_orders()).commit();
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.list_all_list, R.id.list_wait_for_pay, R.id.list_wait_for_receive, R.id.list_wait_for_evaluate, R.id.list_completed})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.list_all_list:
                manager.beginTransaction().replace(R.id.list_framelayout,new All_orders()).commit();
                break;
            case R.id.list_wait_for_pay:
                manager.beginTransaction().replace(R.id.list_framelayout,new obligation()).commit();
                break;
            case R.id.list_wait_for_receive:
                manager.beginTransaction().replace(R.id.list_framelayout,new receiving()).commit();
                break;
            case R.id.list_wait_for_evaluate:
                manager.beginTransaction().replace(R.id.list_framelayout,new remain()).commit();
                break;
            case R.id.list_completed:
                manager.beginTransaction().replace(R.id.list_framelayout,new finished()).commit();
                break;
        }
    }
}
