package com.yingwumeijia.baseywmj.entity.bean;

import java.math.BigDecimal;

/**
 * Created by Jam on 2017/3/25 下午4:47.
 * Describe:
 */

public class BillItemBean {


    /**
     * billId : 0
     * amount : 0
     * postscript :
     * payTime :
     * payTool :
     * revExpType :
     */

    private int billId;
    private BigDecimal amount;
    private String postscript;
    private String payTime;
    private String name;
    private String payTool;
    private int revExpType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
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

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayTool() {
        return payTool;
    }

    public void setPayTool(String payTool) {
        this.payTool = payTool;
    }

    public int getRevExpType() {
        return revExpType;
    }

    public void setRevExpType(int revExpType) {
        this.revExpType = revExpType;
    }
}
