package com.bw.project_demo.ui.fragment.quanzi.presenter;

import com.bw.project_demo.ui.fragment.quanzi.bean.CircleBeans;
import com.bw.project_demo.ui.fragment.quanzi.contance.CircleData;
import com.bw.project_demo.ui.fragment.quanzi.model.CircleModelImpl;

public class CirclePresenterImpl implements CircleData.CirclePresenter {
    CircleData.CircleView circleView;
    private CircleData.CircleModel model;

    @Override
    public void attahView(CircleData.CircleView circleView) {
        this.circleView=circleView;
        model = new CircleModelImpl();
    }

    @Override
    public void requestData(int page) {
        model.ResponseData(page,new CircleData.CircleModel.CallBack() {
            @Override
            public void getData(CircleBeans circleBeans) {
                circleView.showData(circleBeans);
            }
        });
    }
}
