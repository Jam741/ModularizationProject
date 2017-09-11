package com.yingwumeijia.baseywmj.entity.bean;

/**
 * Created by jamisonline on 2017/6/15.
 */

public class ApplyPayMessageBean {

//    {
//        orderId:123,
//                billId:123 ,//账单ID
//            billTypeStr:"设计订单",
//            billAmount:"123.00",
//            content:"支付定金"
//    }

 //   {"orderId":240,"billId":359,"billTypeStr":"设计订单","billAmount":0.01,"content":"支付定金"}


    int orderId;
    int billId;
    String billTypeStr;
    String billAmount;
    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getBillTypeStr() {
        return billTypeStr;
    }

    public void setBillTypeStr(String billTypeStr) {
        this.billTypeStr = billTypeStr;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }



}
