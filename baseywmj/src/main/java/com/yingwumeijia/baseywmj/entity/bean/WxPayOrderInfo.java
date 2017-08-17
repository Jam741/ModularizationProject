package com.yingwumeijia.baseywmj.entity.bean;

/**
 * Created by Jam on 2017/3/17 下午5:46.
 * Describe:
 */

public class WxPayOrderInfo {


    /**
     * appId :
     * partnerId :
     * prepayId :
     * packageStr :
     * nonceStr :
     * timeStamp :
     * sign :
     */

    private String appId;
    private String partnerId;
    private String prepayId;
    private String packageStr;
    private String nonceStr;
    private String timeStamp;
    private String sign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPackageStr() {
        return packageStr;
    }

    public void setPackageStr(String packageStr) {
        this.packageStr = packageStr;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
