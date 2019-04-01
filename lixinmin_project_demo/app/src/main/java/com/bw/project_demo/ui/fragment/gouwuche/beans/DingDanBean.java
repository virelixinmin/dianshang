package com.bw.project_demo.ui.fragment.gouwuche.beans;

public class DingDanBean {
    private int commodityId;
    private int amount;

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public DingDanBean(int commodityId, int amount) {
        this.commodityId = commodityId;
        this.amount = amount;
    }

    public DingDanBean() {
    }
}
