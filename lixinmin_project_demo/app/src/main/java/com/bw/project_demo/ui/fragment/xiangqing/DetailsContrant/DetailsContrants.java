package com.bw.project_demo.ui.fragment.xiangqing.DetailsContrant;

import com.bw.project_demo.ui.fragment.xiangqing.DetailsBeans.beans;

public interface DetailsContrants {
    public interface DetailsView{
        public void showData(beans beans);
    }
    public interface DetailsPresenter<DetailsView>{
        public void attahView(DetailsContrants.DetailsView detailsView);
        public void requestModel(int userId, String sessionId, int id);

    }
    public interface DetailsModel{
        public void responseModel(int userId, String sessionId, int id, DetailsCallBack callBack);
        public interface DetailsCallBack{
            public void getData(beans beans);
        }
    }
}
