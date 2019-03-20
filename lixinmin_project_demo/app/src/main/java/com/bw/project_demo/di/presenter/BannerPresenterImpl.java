package com.bw.project_demo.di.presenter;

import com.bw.project_demo.data.beans.BannerBeans;
import com.bw.project_demo.data.beans.ShopBeans;
import com.bw.project_demo.di.Contract.ContractAll;
import com.bw.project_demo.di.model.BannerModelImpl;

public class BannerPresenterImpl implements ContractAll.BannerPresenter {

    private ContractAll.BannerModel model;
    ContractAll.BannerView bannerView;
    @Override
    public void attchView(ContractAll.BannerView bannerView) {
        this.bannerView = bannerView;
        model = new BannerModelImpl();
    }

    @Override
    public void requestModel() {
        model.responseData(new ContractAll.BannerModel.BannerCallBack() {
            @Override
            public void getData(BannerBeans bannerBeans) {
                bannerView.ShowData(bannerBeans);
            }
        });
    }

    @Override
    public void ShopAttchView(ContractAll.BannerView bannerView) {

    }

    @Override
    public void ShoprequestModel() {
        model.ShopresponseData(new ContractAll.BannerModel.ShopCallBack() {
            @Override
            public void ShopgetData(ShopBeans shopBeans) {
                bannerView.ShopShowData(shopBeans);
            }
        });
    }
}
