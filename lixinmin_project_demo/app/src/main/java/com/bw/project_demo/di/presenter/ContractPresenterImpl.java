package com.bw.project_demo.di.presenter;

import com.bw.project_demo.data.beans.RegisterBean;
import com.bw.project_demo.di.Contract.ContractAll;
import com.bw.project_demo.di.model.ContractModelImpl;

public class ContractPresenterImpl implements ContractAll.ContractPresenter {

    private ContractAll.ContractModel model;
    ContractAll.ContractView contractView;
    @Override
    public void attchView(ContractAll.ContractView contractView) {
        this.contractView=contractView;
        model = new ContractModelImpl();

    }

    @Override
    public void requestModel(String registerphone, String registerpwd) {
        model.responseData(registerphone, registerpwd, new ContractAll.ContractModel.CallBack() {
            @Override
            public void getData(Object s) {
                contractView.ShowData(s);
            }
        });

    }
}
