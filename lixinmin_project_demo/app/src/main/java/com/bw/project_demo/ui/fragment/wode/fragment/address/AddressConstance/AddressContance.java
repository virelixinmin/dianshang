package com.bw.project_demo.ui.fragment.wode.fragment.address.AddressConstance;

import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressBeans.AddressBean;

import java.util.List;

public interface AddressContance {
    public interface AddressView{
        public void showData(List<AddressBean.ResultBean> result);


    }
    public interface AddressPresenter<AddressView>{
        public void attahView(AddressContance.AddressView addressView);
        public void requestModel(int userId, String sessionId);

    }
    public interface AddressModel{
        public void responseModel(int userId, String sessionId, CallBack callBack);
        public interface CallBack{
            public void getData(List<AddressBean.ResultBean> result);


        }
    }
}
