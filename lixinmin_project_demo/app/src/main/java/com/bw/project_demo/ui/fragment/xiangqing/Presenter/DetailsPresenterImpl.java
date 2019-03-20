package com.bw.project_demo.ui.fragment.xiangqing.Presenter;

import com.bw.project_demo.ui.fragment.xiangqing.DetailsBeans.beans;
import com.bw.project_demo.ui.fragment.xiangqing.DetailsContrant.DetailsContrants;
import com.bw.project_demo.ui.fragment.xiangqing.Model.DetailsModelImpl;

public class DetailsPresenterImpl implements DetailsContrants.DetailsPresenter {
    DetailsContrants.DetailsView detailsView;
    private DetailsContrants.DetailsModel model;

    @Override
    public void attahView(DetailsContrants.DetailsView detailsView) {
        this.detailsView=detailsView;
        model = new DetailsModelImpl();
    }

    @Override
    public void requestModel(int userId, String sessionId, int id) {
        model.responseModel(userId,sessionId,id, new DetailsContrants.DetailsModel.DetailsCallBack() {
            @Override
            public void getData(beans beans) {
                detailsView.showData(beans);
            }
        });
    }
}
