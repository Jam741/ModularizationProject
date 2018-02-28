package com.yingwumeijia.baseywmj.entity.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Jam on 2017/3/25 上午10:51.
 * Describe:
 */

public class CouponListResponseBean implements Serializable {


    /**
     * totalCount : 4
     * currentPageNum : 1
     * totalPageNum : 1
     * result : [{"activity":{"id":21,"name":"观点或观点或","scene":1,"coverImage":"http://o8nljewkg.bkt.clouddn.com/o_1bbvni5ie77k1l979lm6mb143e7.png","beginTime":1490090400000,"endTime":1490975700000,"subtitle":"个地方看到","sign":"fa4b5b592e064a609b00d2a49f0f51f4","status":1,"available":true,"createTime":1490088708000,"updateTime":1490424768000,"justStatusDesc":"进行中","justStatus":1,"sceneDesc":"小区活动"},"activityCoupon":{"id":18,"name":"观点或观点或","charge":false,"beginTime":1490086800000,"endTime":1490975700000,"available":true,"createTime":1490088708000,"updateTime":1490345059000},"customerCoupon":{"id":15,"activityId":21,"couponId":18,"userId":219,"userName":"拉进来","userPhone":"13608093402","couponCode":"mCBCPe2Nnvev","couponUsed":false,"couponUseType":0,"couponGetTime":1490265757000,"available":true}},{"activity":{"id":24,"name":"很多时候是的","scene":1,"coverImage":"http://o8nljewkg.bkt.clouddn.com/o_1bbq24273i2j104p17bf1fc319n87.jpeg","beginTime":1490162700000,"endTime":1490975700000,"subtitle":"3432432","sign":"639fc506b928459db489d8fdf4eb1dcb","status":0,"available":true,"createTime":1490154955000,"updateTime":1490424471000,"justStatusDesc":"已下架","justStatus":3,"sceneDesc":"小区活动"},"activityCoupon":{"id":21,"name":"发生大洪水的","charge":false,"beginTime":1490151600000,"endTime":1490975700000,"available":true,"createTime":1490154955000,"updateTime":1490265690000},"customerCoupon":{"id":14,"activityId":24,"couponId":21,"userId":219,"userName":"家居","userPhone":"13608093402","couponCode":"fnDsGG2NLqJD","couponUsed":false,"couponUseType":0,"couponGetTime":1490265749000,"available":true}},{"activity":{"id":12,"name":"测试活动","scene":1,"coverImage":"http://o8nljewkg.bkt.clouddn.com/o_1astf00qu1kv91gp616ue1gverfas.jpg","beginTime":1489939200000,"endTime":1490889600000,"subtitle":"测试活动副标题","sign":"a84fbf029ff6492b98f706aef3d34462","status":0,"available":true,"createTime":1490006131000,"updateTime":1490424486000,"justStatusDesc":"已下架","justStatus":3,"sceneDesc":"小区活动"},"activityCoupon":{"id":9,"name":"测试活动券","charge":true,"price":0.01,"beginTime":1489939200000,"endTime":1490889600000,"available":true,"createTime":1490006131000,"updateTime":1490355628000},"customerCoupon":{"id":13,"activityId":12,"couponId":9,"userId":219,"userName":"具体","userPhone":"13608093402","couponCode":"xkWGnA2NkknY","couponUsed":false,"couponUseType":0,"couponGetTime":1490265737000,"available":true}},{"activity":{"id":18,"name":"特利特了","scene":2,"coverImage":"http://o8nljewkg.bkt.clouddn.com/o_1bbo1lqb916vc18f9ce61iavnauc.png","beginTime":1489028400000,"endTime":1490868000000,"subtitle":"特了","sign":"cf1ecfb5689a40e181c12149a338da82","status":0,"available":true,"createTime":1490087307000,"updateTime":1490424478000,"justStatusDesc":"已下架","justStatus":3,"sceneDesc":"卖场活动"},"activityCoupon":{"id":15,"name":"特利特了","charge":true,"price":4343,"beginTime":1489471200000,"endTime":1490961300000,"available":true,"createTime":1490087307000,"updateTime":1490265545000},"customerCoupon":{"id":12,"activityId":18,"couponId":15,"userId":219,"userName":"突击","userPhone":"13608093402","couponCode":"iRsqCn2NWPnF","couponUsed":false,"couponUseType":0,"couponGetTime":1490265473000,"available":true}}]
     */

    private int totalCount;
    private int currentPageNum;
    private int totalPageNum;
    private List<ResultBean> result;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {


        /**
         * id : 0
         * type :
         * typeDesc :
         * name :
         * charge : false
         * amount : 0
         * beginTime :
         * endTime :
         * available : false
         * couponUsed : false
         * countDown :
         */

        private int id;
        private int type;//（1 = 设计抵扣券，2 = 装修抵扣券，3 = 活动入场券）,
        private String typeDesc;
        private String name;
        private boolean charge;
        private BigDecimal amount;
        private String beginTime;
        private String endTime;
        private boolean available;
        private boolean couponUsed;
        private int countDown;
        private String description;
        private boolean effective;


        public boolean isEffective() {
            return effective;
        }

        public void setEffective(boolean effective) {
            this.effective = effective;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isCharge() {
            return charge;
        }

        public void setCharge(boolean charge) {
            this.charge = charge;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public boolean isCouponUsed() {
            return couponUsed;
        }

        public void setCouponUsed(boolean couponUsed) {
            this.couponUsed = couponUsed;
        }

        public int getCountDown() {
            return countDown;
        }

        public void setCountDown(int countDown) {
            this.countDown = countDown;
        }
    }
}
