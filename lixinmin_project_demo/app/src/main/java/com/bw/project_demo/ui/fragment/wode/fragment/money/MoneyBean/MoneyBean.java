package com.bw.project_demo.ui.fragment.wode.fragment.money.MoneyBean;

import java.util.List;

public class MoneyBean {
    private ResultBean result;
    private String message;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

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

    public static class ResultBean {
        /**
         * balance : 99997841
         * detailList : [{"amount":611,"consumerTime":1550826277000,"orderId":"201902211657231588","userId":8}]
         */

        private int balance;
        private List<DetailListBean> detailList;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public List<DetailListBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<DetailListBean> detailList) {
            this.detailList = detailList;
        }

        public static class DetailListBean {
            /**
             * amount : 611
             * consumerTime : 1550826277000
             * orderId : 201902211657231588
             * userId : 8
             */

            private int amount;
            private long consumerTime;
            private String orderId;
            private int userId;

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public long getConsumerTime() {
                return consumerTime;
            }

            public void setConsumerTime(long consumerTime) {
                this.consumerTime = consumerTime;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
