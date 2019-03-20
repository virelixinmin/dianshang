package com.bw.project_demo.di.Contract;

import com.bw.project_demo.data.beans.BannerBeans;
import com.bw.project_demo.data.beans.ShopBeans;

public interface ContractAll {


    public interface ContractView{
        public void ShowData(Object s);

    }
    public interface ContractPresenter<ContractView>{
        public void attchView(ContractAll.ContractView contractView);
        public void requestModel(String registerphone, String registerpwd);


        }
    public interface ContractModel{
        public void responseData(String registerphone,String registerpwd,CallBack callBack);
        public interface CallBack{
            public void getData(Object s);

        }
    }
    public interface BannerView{
        public void ShowData(BannerBeans bannerBeans);

        public void ShopShowData(ShopBeans shopBeans);

    }
    public interface BannerPresenter<ContractView>{
        public void attchView(BannerView bannerView);
        public void requestModel();

        public void ShopAttchView(BannerView bannerView);
        public void ShoprequestModel();
    }
    public interface BannerModel{
        public void responseData(BannerCallBack callBack);
        public interface BannerCallBack{
            public void getData(BannerBeans bannerBeans);
        }

        public void ShopresponseData(ShopCallBack shopCallBack);
        public interface ShopCallBack{
            public void ShopgetData(ShopBeans shopBeans);
        }
    }



}
