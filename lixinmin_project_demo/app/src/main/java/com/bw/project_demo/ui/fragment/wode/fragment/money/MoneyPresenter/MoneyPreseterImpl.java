package com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyPresenter;

import com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyBean.MoneyBean;
import com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyConstance.MoneyStance;
import com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyModel.MoneyModelImpl;

public class MoneyPreseterImpl implements MoneyStance.MoneyPresenter {
    MoneyStance.MoneyView moneyView;
    private MoneyStance.MoneyModel model;

    @Override
    public void attahView(MoneyStance.MoneyView moneyView) {
        this.moneyView=moneyView;
        model = new MoneyModelImpl();
    }

    @Override
    public void requestModel(int userId, String sessionId) {
        model.ResponseModel(userId,sessionId,new MoneyStance.MoneyModel.MoenyCallBack() {
            @Override
            public void getData(MoneyBean detailList) {
                moneyView.showData(detailList);
            }
        });
    }
}
