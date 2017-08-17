package com.yingwumeijia.android.ywmj.client.function.cashier;

/**
 * Created by Jam on 2017/3/22 下午4:57.
 * Describe:
 */



public class PayTypeBean {


    public int image;

    public String name;

    public PayType payType;

    public PayTypeBean(int image, String name, PayType payType) {
        this.image = image;
        this.name = name;
        this.payType = payType;
    }
}
