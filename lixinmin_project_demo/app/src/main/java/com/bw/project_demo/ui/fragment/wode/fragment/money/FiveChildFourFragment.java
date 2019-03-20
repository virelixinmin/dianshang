package com.bw.project_demo.ui.fragment.wode.fragment.money;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyBean.MoneyBean;
import com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyConstance.MoneyStance;
import com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyModel.MoneyModelImpl;
import com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyPresenter.MoneyPreseterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FiveChildFourFragment extends Fragment implements MoneyStance.MoneyView {
    @BindView(R.id.my_wallet_price)
    TextView myWalletPrice;
    @BindView(R.id.my_wallet_image)
    RelativeLayout myWalletImage;
    @BindView(R.id.my_wallet_title)
    LinearLayout myWalletTitle;
    @BindView(R.id.my_wallet_view)
    View myWalletView;
    @BindView(R.id.my_wallet_ryl)
    RecyclerView myWalletRyl;
    Unbinder unbinder;
    private MoneyStance.MoneyPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getActivity(), R.layout.five_child_four, null);
        SharedPreferences data = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        int userId = data.getInt("userId", 184);
        String sessionId = data.getString("sessionId", "15011445417");
        unbinder = ButterKnife.bind(this, inflate);
        presenter = new MoneyPreseterImpl();
        presenter.attahView(this);
        presenter.requestModel(userId,sessionId);
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showData(MoneyBean detailList) {
        Toast.makeText(getActivity(), "detailList.getResult().getBalance():" + detailList.getResult().getBalance(), Toast.LENGTH_SHORT).show();
        myWalletPrice.setText(detailList.getResult().getBalance()+"");
        List<MoneyBean.ResultBean.DetailListBean> detailList1 = detailList.getResult().getDetailList();
        MyMoneyAdapter adapter = new MyMoneyAdapter(R.layout.my_money_item,detailList1);
        myWalletRyl.setLayoutManager(new LinearLayoutManager(getActivity()));
        myWalletRyl.setAdapter(adapter);
    }
}
