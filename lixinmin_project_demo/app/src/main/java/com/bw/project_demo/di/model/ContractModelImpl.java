package com.bw.project_demo.di.model;

import android.util.Log;

import com.bw.project_demo.data.beans.RegisterBean;
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.di.Contract.ContractAll;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ContractModelImpl implements ContractAll.ContractModel {






    @Override
    public void responseData(String registerphone, String registerpwd, final CallBack callBack) {
        String urlString =CheckPath.urlRegister+"?phone="+registerphone+"&&pwd="+registerpwd;

        OkGo.<String>post(urlString).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                try {
                    String s = response.body().toString();
                    JSONObject jsonObject = new JSONObject(s);
                    Object message = jsonObject.get("message");
                    Log.d("ContractModelImpl", "message:" + message);
                    callBack.getData(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
