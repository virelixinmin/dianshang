package com.bw.project_demo.ui.fragment.wode.fragment.address.AddressUpdate.UpdatePresenter;

import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressUpdate.UpdateAddressConstance.UpdateConstance;
import com.bw.project_demo.ui.fragment.wode.fragment.address.AddressUpdate.UpdateModel.UpdateModelImpl;

import java.util.HashMap;

public class UpdatePresenterImpl implements UpdateConstance.UpdatePresenter<UpdateConstance.UpdateView> {
    UpdateConstance.UpdateView updateView;
    private UpdateConstance.UpdateModel model;

    @Override
    public void attachView(UpdateConstance.UpdateView updateView) {
        this.updateView=updateView;
        model = new UpdateModelImpl();
    }

    @Override
    public void requestUpdateModel(HashMap<String, String> map2, HashMap<String, String> map) {
        model.responseModel(map2,map,new UpdateConstance.UpdateModel.AddressCallBack() {
            @Override
            public void getUpdateData(String s) {
                updateView.showUpdateData(s);
            }
        });
    }

    @Override
    public void requestAddModel(HashMap<String, String> map2, HashMap<String, String> map) {
            model.responseAddModel(map2,map,new UpdateConstance.UpdateModel.AddressAddCallBack() {
                @Override
                public void getAddData(String string) {
                    updateView.showAddData(string);
                }
            });
    }
}
