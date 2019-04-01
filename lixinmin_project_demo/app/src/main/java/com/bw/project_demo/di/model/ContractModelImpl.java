package com.bw.project_demo.di.model;

import android.util.Log;

import com.bw.project_demo.data.beans.RegisterBean;
import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.di.Contract.ContractAll;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ContractModelImpl implements ContractAll.ContractModel {
    @Override
    public void responseData(String registerphone, String registerpwd, final CallBack callBack) {
        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class).getRegister(registerphone,registerpwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String s = responseBody.string().toString();
                        JSONObject jsonObject = new JSONObject(s);
                        Object messgae = jsonObject.get("message");
                        callBack.getData(messgae);

                    }
                });
    }
}
