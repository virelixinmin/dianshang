package com.bw.project_demo.ui.fragment.wode.fragment.mycircle.presenter;

import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.beans.circlebean;
import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.circleConstance.CircleConstance;
import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.model.CircleModelImpl;

import java.util.List;

public class CirclePresenterImpl implements CircleConstance.CirclePresenter {
    CircleConstance.CircleView circleView;
    private CircleConstance.CircleModel model;

    @Override
    public void attahView(CircleConstance.CircleView circleView) {
        this.circleView=circleView;
        model = new CircleModelImpl();

    }

    @Override
    public void requestModel(String sessionId, int userId) {
        model.responseModel(sessionId,userId,new CircleConstance.CircleModel.CallBack() {
            @Override
            public void getData(List<circlebean.ResultBean> result) {
                circleView.showData(result);
            }
        });
    }
}
