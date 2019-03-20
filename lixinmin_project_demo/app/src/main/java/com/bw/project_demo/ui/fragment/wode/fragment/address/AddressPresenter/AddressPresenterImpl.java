package com.bw.project_demo.ui.fragment.wode.fragment.address.AddressPresenter;

import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressBeans.AddressBean;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressConstance.AddressContance;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressModel.AddressModelmpl;

import java.util.List;

public class AddressPresenterImpl implements AddressContance.AddressPresenter {

    AddressContance.AddressView addressView;
    private AddressContance.AddressModel model;

    @Override
    public void attahView(AddressContance.AddressView addressView) {
        this.addressView=addressView;
        model = new AddressModelmpl();
    }

    @Override
    public void requestModel(int userId, String sessionId) {
        model.responseModel(userId,sessionId,new AddressContance.AddressModel.CallBack() {
            @Override
            public void getData(List<AddressBean.ResultBean> result) {
                addressView.showData(result);
            }
        });
    }
}
