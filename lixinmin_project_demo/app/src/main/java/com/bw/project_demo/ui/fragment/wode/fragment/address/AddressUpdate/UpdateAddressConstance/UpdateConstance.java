package com.bw.project_demo.ui.fragment.wode.fragment.address.AddressUpdate.UpdateAddressConstance;

import java.util.HashMap;

public interface UpdateConstance {
    public interface UpdateView{
        public void showUpdateData(String s);
        public void showAddData(String string);
    }
    public interface UpdatePresenter<UpdateView>{
        public void attachView(UpdateConstance.UpdateView updateView);
        public void requestUpdateModel(HashMap<String, String> map2, HashMap<String, String> map);
        public void requestAddModel(HashMap<String, String> map2, HashMap<String, String> map);
    }
    public interface UpdateModel{
        public void responseAddModel(HashMap<String, String> map2, HashMap<String, String> map, AddressAddCallBack addressAddCallBack);
        public void responseModel(HashMap<String, String> map2, HashMap<String, String> map, AddressCallBack addressCallBack);
        public interface AddressCallBack{
            public void getUpdateData(String s);

        }
        public interface AddressAddCallBack{
            public void getAddData(String string);
        }
    }
}
