package com.yingwumeijia.baseywmj.entity.bean;

import java.math.BigDecimal;

/**
 * Created by Jam on 2017/5/9 下午1:53.
 * Describe:
 */

public class CouponDetail {


    /**
     * id : 0
     * type :
     * typeDesc :
     * name :
     * charge : false
     * amount : 0
     * beginTime :
     * endTime :
     * couponCode :
     * available : false
     * couponUsed : false
     * description :
     * usageRules :
     * countDown :
     * relatedActivity : {"id":0,"name":"","scene":"","coverImage":"","beginTime":"","endTime":"","sceneDesc":""}
     */

    private int id;
    private String type;
    private String typeDesc;
    private String name;
    private boolean charge;
    private BigDecimal amount;
    private String beginTime;
    private String endTime;
    private String couponCode;
    private boolean available;
    private boolean couponUsed;
    private String description;
    private String usageRules;
    private int countDown;
    private boolean effective;
    private RelatedActivityBean relatedActivity;

    public boolean isEffective() {
        return effective;
    }

    public void setEffective(boolean effective) {
        this.effective = effective;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsageRules() {
        return usageRules;
    }

    public void setUsageRules(String usageRules) {
        this.usageRules = usageRules;
    }

    public int getCountDown() {
        return countDown;
    }

    public void setCountDown(int countDown) {
        this.countDown = countDown;
    }

    public RelatedActivityBean getRelatedActivity() {
        return relatedActivity;
    }

    public void setRelatedActivity(RelatedActivityBean relatedActivity) {
        this.relatedActivity = relatedActivity;
    }

    public static class RelatedActivityBean {
        /**
         * id : 0
         * name :
         * scene :
         * coverImage :
         * beginTime :
         * endTime :
         * sceneDesc :
         */

        private int id;
        private String name;
        private String scene;
        private String coverImage;
        private String beginTime;
        private String endTime;
        private String sceneDesc;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScene() {
            return scene;
        }

        public void setScene(String scene) {
            this.scene = scene;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
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

        public String getSceneDesc() {
            return sceneDesc;
        }

        public void setSceneDesc(String sceneDesc) {
            this.sceneDesc = sceneDesc;
        }
    }
}
