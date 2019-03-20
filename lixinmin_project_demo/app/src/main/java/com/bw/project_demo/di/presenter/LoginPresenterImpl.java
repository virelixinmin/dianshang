package com.bw.project_demo.di.presenter;

import com.bw.project_demo.di.Contract.LoginContractAll;
import com.bw.project_demo.di.model.LoginModelImpl;

public class LoginPresenterImpl implements LoginContractAll.LoginPresenter {

    private LoginContractAll.LoginModel model;
    LoginContractAll.LoginView loginView;
    @Override
    public void attahView(LoginContractAll.LoginView loginView) {
        this.loginView=loginView;
        model = new LoginModelImpl();

    }

    @Override
    public void requestModel(String editphone, String editpwd) {
        model.response(editphone, editpwd, new LoginContractAll.LoginModel.CallBack() {
            @Override
            public void getData(String result) {
                loginView.ShowData(result);
            }
        });
    }
}
