package com.bw.project_demo.ui.fragment.quanzi.bean;

import java.util.List;

public class CircleBeans {

    /**
     * result : [{"commodityId":1,"content":"??????","createTime":1550708898000,"greatNum":0,"headPic":"http://172.17.8.100/images/small/head_pic/2019-02-20/20190220185655.png","id":481,"image":"http://172.17.8.100/images/small/circle_pic/2019-02-20/5816720190220182818.png","nickName":"ds","userId":124,"whetherGreat":2},{"commodityId":1,"content":"??????","createTime":1550708614000,"greatNum":0,"headPic":"http://172.17.8.100/images/small/head_pic/2019-02-20/20190220185655.png","id":480,"image":"http://172.17.8.100/images/small/circle_pic/2019-02-20/1783920190220182334.png","nickName":"ds","userId":124,"whetherGreat":2},{"commodityId":1,"content":"??????","createTime":1550708333000,"greatNum":0,"headPic":"http://172.17.8.100/images/small/head_pic/2019-02-20/20190220185655.png","id":479,"image":"http://172.17.8.100/images/small/circle_pic/2019-02-20/2358120190220181853.png","nickName":"ds","userId":124,"whetherGreat":2},{"commodityId":1,"content":"??????","createTime":1550708050000,"greatNum":0,"headPic":"http://172.17.8.100/images/small/head_pic/2019-02-20/20190220185655.png","id":478,"image":"http://172.17.8.100/images/small/circle_pic/2019-02-20/7620420190220181410.png","nickName":"ds","userId":124,"whetherGreat":2},{"commodityId":1,"content":"??????","createTime":1550707765000,"greatNum":0,"headPic":"http://172.17.8.100/images/small/head_pic/2019-02-20/20190220185655.png","id":477,"image":"http://172.17.8.100/images/small/circle_pic/2019-02-20/9584720190220180925.png","nickName":"ds","userId":124,"whetherGreat":2},{"commodityId":1,"content":"??????","createTime":1550707482000,"greatNum":0,"headPic":"http://172.17.8.100/images/small/head_pic/2019-02-20/20190220185655.png","id":476,"image":"http://172.17.8.100/images/small/circle_pic/2019-02-20/4967020190220180442.png","nickName":"ds","userId":124,"whetherGreat":2}]
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

    public static class ResultBean {
        /**
         * commodityId : 1
         * content : ??????
         * createTime : 1550708898000
         * greatNum : 0
         * headPic : http://172.17.8.100/images/small/head_pic/2019-02-20/20190220185655.png
         * id : 481
         * image : http://172.17.8.100/images/small/circle_pic/2019-02-20/5816720190220182818.png
         * nickName : ds
         * userId : 124
         * whetherGreat : 2
         */

        private int commodityId;
        private String content;
        private long createTime;
        private int greatNum;
        private String headPic;
        private int id;
        private String image;
        private String nickName;
        private int userId;
        private int whetherGreat;

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getGreatNum() {
            return greatNum;
        }

        public void setGreatNum(int greatNum) {
            this.greatNum = greatNum;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getWhetherGreat() {
            return whetherGreat;
        }

        public void setWhetherGreat(int whetherGreat) {
            this.whetherGreat = whetherGreat;
        }
    }
}
