package com.yingwumeijia.baseywmj.entity.bean;

import java.math.BigDecimal;

/**
 * Created by Jam on 2017/3/22 下午1:41.
 * Describe:
 */

public class PayBillInfo {

    /**
     * sessionId : 0
     * companyId : 0
     * serviceContentType :
     * amount : 0
     * postscript :
     */

    private int sessionId;
    private int userId;
    private int serviceContentType = -1;
    private BigDecimal amount;
    private String postscript;

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getServiceContentType() {
        return serviceContentType;
    }

    public void setServiceContentType(int serviceContentType) {
        this.serviceContentType = serviceContentType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPostscript() {
        return postscript;
    }

    public void setPostscript(String postscript) {
        this.postscript = postscript;
    }
}
