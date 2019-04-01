package com.bw.project_demo.di.model;

import com.bw.project_demo.data.beans.LoginBean;
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.di.Contract.LoginContractAll;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class LoginModelImpl implements LoginContractAll.LoginModel {

    @Override
    public void response(String editphone, String editpwd, final CallBack callBack) {
        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
                .getLogin(editphone,editpwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String s = responseBody.string().toString();
                        callBack.getData(s);
                    }
                });
    }
}
