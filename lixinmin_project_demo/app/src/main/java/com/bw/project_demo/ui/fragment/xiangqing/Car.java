package com.bw.project_demo.ui.fragment.xiangqing;

public class Car {
    int commodityId;
    int count ;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Car(int commodityId, int count) {
        this.commodityId = commodityId;
        this.count = count;
    }

    public Car() {
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }
}
