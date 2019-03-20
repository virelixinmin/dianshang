package com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyConstance;

import com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyBean.MoneyBean;

public interface MoneyStance {
    public interface MoneyView{
        public void showData(MoneyBean detailList);
    }
    public interface MoneyPresenter<MoneyView>{
        public void attahView(MoneyStance.MoneyView moneyView);
        public void requestModel(int userId, String sessionId);
    }
    public interface MoneyModel{
        public void ResponseModel(int userId, String sessionId, MoenyCallBack moenyCallBack);
        public interface MoenyCallBack{
            public void getData(MoneyBean detailList);
        }
    }
}
