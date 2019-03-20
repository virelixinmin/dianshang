package com.bw.project_demo.di.presenter;

import com.bw.project_demo.data.beans.FindShoppingBean;
import com.bw.project_demo.di.Contract.ShoppingContract;
import com.bw.project_demo.di.model.ShoppingModelImpl;

public class ShoppingPresenterImpl implements ShoppingContract.ShoppingPresenter {


    private ShoppingContract.ShoppingModel model;
    ShoppingContract.ShoppingView shoppingView;
    @Override
    public void attahView(ShoppingContract.ShoppingView shoppingView) {
        this.shoppingView=shoppingView;
        model = new ShoppingModelImpl();

    }

    @Override
    public void requestModel(int userId, String sessionId) {
        model.responseModel(userId,sessionId,new ShoppingContract.ShoppingModel.CallBack() {
            @Override
            public void getData(FindShoppingBean shoppingBeans) {
                shoppingView.showData(shoppingBeans);
            }
        });
    }
}
