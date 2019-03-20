package com.bw.project_demo.ui.fragment.quanzi.contance;

import com.bw.project_demo.ui.fragment.quanzi.bean.CircleBeans;

public interface CircleData {
    public interface CircleView{
        public void showData(CircleBeans circleBeans);
    }
    public interface CirclePresenter<circleData>{
        public void attahView(CircleView circleView);
        public void requestData(int page);
    }
    public interface CircleModel{
        public void ResponseData(int page, CallBack callBack);
        public interface CallBack{
            public void getData(CircleBeans circleBeans);
        }
    }
}
