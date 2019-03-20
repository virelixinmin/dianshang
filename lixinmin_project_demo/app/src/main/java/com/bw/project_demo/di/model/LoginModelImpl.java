package com.bw.project_demo.di.model;

import com.bw.project_demo.data.beans.LoginBean;
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.di.Contract.LoginContractAll;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginModelImpl implements LoginContractAll.LoginModel {

    @Override
    public void response(String editphone, String editpwd, final CallBack callBack) {
        String urlString = CheckPath.urlLogin+"?phone="+editphone+"&pwd="+editpwd;
        OkGo.<String>post(urlString).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                try {
                    String s = response.body().toString();
//                    JSONObject jsonObject = new JSONObject(s);

//                    Object message = jsonObject.get("message");
                    callBack.getData(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }






            }
        });
    }
}
