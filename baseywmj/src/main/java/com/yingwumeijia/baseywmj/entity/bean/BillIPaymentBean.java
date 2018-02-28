package com.yingwumeijia.baseywmj.entity.bean;

import java.math.BigDecimal;

/**
 * Created by Jam on 2017/3/25 下午5:12.
 * Describe:
 */

public class BillIPaymentBean {


    /**
     * billId : 0
     * logo :
     * billContentTypeStr :
     * amount : 0
     * postscript :
     * createTime :
     */

    private int billId;
    private String logo;
    private String billContentTypeStr;
    private BigDecimal amount;
    private String postscript;
    private String createTime;

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBillContentTypeStr() {
        return billContentTypeStr;
    }

    public void setBillContentTypeStr(String billContentTypeStr) {
        this.billContentTypeStr = billContentTypeStr;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
