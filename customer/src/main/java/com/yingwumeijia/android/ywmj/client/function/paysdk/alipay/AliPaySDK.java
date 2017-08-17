package com.yingwumeijia.android.ywmj.client.function.paysdk.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * Created by Jam on 2017/2/23 下午4:43.
 * Describe:
 */

public class AliPaySDK {


    private final Activity context;

    private String APP_ID;

    private String orderInfo;

    private Handler mHandler;

    private boolean showLoading = true;

    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_AUTH_FLAG = 2;

    private AliPaySDK(Builder builder) {
        this.context = builder.context;
        this.APP_ID = builder.APP_ID;
        this.orderInfo = builder.orderInfo;
        this.mHandler = builder.handler;
    }


    public void pay() {
        if (TextUtils.isEmpty(APP_ID) || TextUtils.isEmpty(orderInfo) || mHandler == null) {
            return;
        }

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
                Map<String, String> result = alipay.payV2(orderInfo, showLoading);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    public static class Builder {
        private Activity context;
        private String APP_ID;
        private String orderInfo;
        private Handler handler;
        private boolean showLoading = true;

        public Builder(Activity context) {
            this.context = context;
        }

        public Builder appId(String APP_ID) {
            this.APP_ID = APP_ID;
            return this;
        }

        public Builder orderInfo(String orderInfo) {
            this.orderInfo = orderInfo;
            return this;
        }

        public Builder handler(Handler handler) {
            this.handler = handler;
            return this;
        }

        public Builder showLoading(boolean showLoading) {
            this.showLoading = showLoading;
            return this;
        }

        public AliPaySDK build() {

            return new AliPaySDK(this);
        }

    }
}
