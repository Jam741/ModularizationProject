package com.yingwumeijia.android.ywmj.client.function.paysdk.wxpay;

import android.content.Context;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


/**
 * Created by Jam on 2017/2/23 下午3:29.
 * Describe:
 */

public class WXPaySDK {

    private Context mContext;
    private String mAppId;
    private String partnerId;
    private String mPrepayId;
    private String nonceStr;
    private String timeStamp;
    private String sign;
    private IWXAPI mApi;

    private WXPaySDK(Builder builder) {
        mContext = builder.context;
        mAppId = builder.appId;
        partnerId = builder.parentId;
        mPrepayId = builder.prepayId;
        nonceStr = builder.nonceStr;
        timeStamp = builder.timeStamp;
        sign = builder.sign;
        mApi = WXAPIFactory.createWXAPI(builder.context, builder.appId, true);
        mApi.registerApp(mAppId);
    }


    public static class Builder {

        private Context context;
        private String appId;
        private String parentId;
        private String prepayId;
        private String nonceStr;
        private String timeStamp;
        private String sign;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder appId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder parentId(String parentId) {
            this.parentId = parentId;
            return this;
        }

        public Builder prepayId(String prepayId) {
            this.prepayId = prepayId;
            return this;
        }

        public Builder nonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
            return this;
        }

        public Builder timeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder sign(String sign) {
            this.sign = sign;
            return this;
        }


        public WXPaySDK build() {
            return new WXPaySDK(this);
        }
    }

    public void pay() {
        if (mApi != null) {
            if (isWXAppInstalled()) {
                PayReq req = new PayReq();
                req.appId = mAppId;
                req.partnerId = partnerId;
                req.prepayId = mPrepayId;
                req.packageValue = "Sign=WXPay";
                req.nonceStr = nonceStr;
                req.timeStamp = timeStamp;
                req.sign = sign;
                mApi.sendReq(req);
            }
        }
    }

    private boolean isWXAppInstalled() {
        return mApi.isWXAppInstalled();
    }
}
