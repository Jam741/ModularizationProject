package com.yingwumeijia.baseywmj.entity.bean;

import java.math.BigDecimal;

/**
 * Created by Jam on 2017/3/21 下午4:10.
 * Describe:
 */

public class BillSimpleInfo {


    /**
     * logo :
     * companyName :
     * activityName :
     * serviceContent :
     * serviceContentStr :
     * amount : 0
     * cashAmount : 0
     * couponAmount : 0
     * couponCount : 0
     * revExpType :
     * whetherPayed : false
     * postscript :
     * statusStr :
     * billDetailType :
     * billDetailTypeStr :
     * createTime :
     * payTime :
     * canPay : false
     * canBindCoupons : false
     */

    private String logo;
    private String companyName;
    private String activityName;
    private int serviceContent;
    private String serviceContentStr;
    private BigDecimal amount;
    private BigDecimal cashAmount;
    private BigDecimal couponAmount;
    private BigDecimal handledAmount;
    private int couponCount;
    private int revExpType;
    private boolean whetherPayed;
    private String postscript;
    private String statusStr;
    private int billDetailType;
    private String billDetailTypeStr;
    private String createTime;
    private String payTime;
    private boolean canPay;
    private boolean canBindCoupons;

    public BigDecimal getHandledAmount() {
        return handledAmount;
    }

    public void setHandledAmount(BigDecimal handledAmount) {
        this.handledAmount = handledAmount;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(int serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getServiceContentStr() {
        return serviceContentStr;
    }

    public void setServiceContentStr(String serviceContentStr) {
        this.serviceContentStr = serviceContentStr;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public int getRevExpType() {
        return revExpType;
    }

    public void setRevExpType(int revExpType) {
        this.revExpType = revExpType;
    }

    public boolean isWhetherPayed() {
        return whetherPayed;
    }

    public void setWhetherPayed(boolean whetherPayed) {
        this.whetherPayed = whetherPayed;
    }

    public String getPostscript() {
        return postscript;
    }

    public void setPostscript(String postscript) {
        this.postscript = postscript;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public int getBillDetailType() {
        return billDetailType;
    }

    public void setBillDetailType(int billDetailType) {
        this.billDetailType = billDetailType;
    }

    public String getBillDetailTypeStr() {
        return billDetailTypeStr;
    }

    public void setBillDetailTypeStr(String billDetailTypeStr) {
        this.billDetailTypeStr = billDetailTypeStr;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public boolean isCanPay() {
        return canPay;
    }

    public void setCanPay(boolean canPay) {
        this.canPay = canPay;
    }

    public boolean isCanBindCoupons() {
        return canBindCoupons;
    }

    public void setCanBindCoupons(boolean canBindCoupons) {
        this.canBindCoupons = canBindCoupons;
    }
}
