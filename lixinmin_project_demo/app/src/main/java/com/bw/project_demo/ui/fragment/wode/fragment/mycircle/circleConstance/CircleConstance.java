package com.bw.project_demo.ui.fragment.wode.fragment.mycircle.circleConstance;

import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.beans.circlebean;

import java.util.List;

public interface CircleConstance {
    public interface CircleView{
        public void showData(List<circlebean.ResultBean> result);
    }
    public interface CirclePresenter<CircleView>{
        public void attahView(CircleConstance.CircleView circleView);
        public void requestModel(String sessionId, int userId);
    }
    public interface CircleModel{
        public void responseModel(String sessionId, int userId, CallBack callBack);
        public interface CallBack{
            public void getData(List<circlebean.ResultBean> result);
        }
    }
}
