package com.bw.project_demo.di.Contract;

public interface LoginContractAll {
    public interface LoginView{
        public void ShowData(String s);
    }
    public interface LoginPresenter<LoginView>{
        public void attahView(LoginContractAll.LoginView loginView);
        public void requestModel(String editphone, String editpwd);
    }
    public interface LoginModel{
        public void response(String editphone,String editpwd,CallBack callBack);
        public interface CallBack{
            public void getData(String s);
        }
    }
}
