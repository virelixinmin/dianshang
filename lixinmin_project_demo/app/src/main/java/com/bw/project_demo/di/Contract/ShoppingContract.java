package com.bw.project_demo.di.Contract;

import com.bw.project_demo.data.beans.FindShoppingBean;

public interface ShoppingContract {
    public interface ShoppingView{
        public void showData(FindShoppingBean shoppingBeans);
    }
    public interface ShoppingPresenter<ShoppingView>{
        public void attahView(ShoppingContract.ShoppingView shoppingView);
        public void requestModel(int userId, String sessionId);
    }
    public interface ShoppingModel{
        public void responseModel(int userId, String sessionIdl, CallBack callBack);
        public interface CallBack{
            public void getData(FindShoppingBean shoppingBeans);
        }
    }
}
