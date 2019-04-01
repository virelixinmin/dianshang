package com.bw.project_demo.data.beans;

import java.io.Serializable;
import java.util.List;

/**
 * 文件描述：
 * 作者：${尘}
 * 创建时间：2019/1/9  21:00
 * 更改时间：2019/1/9  21:00
 * 版本号：1
 */
//查询购物车的bean类
public class FindShoppingBean {


    /**
     * result : [{"commodityId":5,"commodityName":"双头两用修容笔","count":3,"pic":"http://mobile.bwstudent.com/images/small/commodity/mzhf/cz/3/1.jpg","price":39},{"commodityId":6,"commodityName":"轻柔系自然裸妆假睫毛","count":4,"pic":"http://mobile.bwstudent.com/images/small/commodity/mzhf/cz/4/1.jpg","price":39}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * commodityId : 5
         * commodityName : 双头两用修容笔
         * count : 3
         * pic : http://mobile.bwstudent.com/images/small/commodity/mzhf/cz/3/1.jpg
         * price : 39
         */

        private int commodityId;
        private String commodityName;
        private int count;
        private String pic;
        private int price;
        private boolean checked;

        public ResultBean(int commodityId, String commodityName, int count, String pic, int price, boolean checked) {
            this.commodityId = commodityId;
            this.commodityName = commodityName;
            this.count = count;
            this.pic = pic;
            this.price = price;
            this.checked = checked;
        }

        public ResultBean() {
        }

        public boolean getChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }


        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "commodityId=" + commodityId +
                    ", commodityName='" + commodityName + '\'' +
                    ", count=" + count +
                    ", pic='" + pic + '\'' +
                    ", price=" + price +
                    ", checked=" + checked +
                    '}';
        }
    }
}
