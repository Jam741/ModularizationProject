package com.yingwumeijia.android.ywmj.client.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yingwumeijia.android.ywmj.client.R;
import com.yingwumeijia.baseywmj.constant.Constant;
import com.yingwumeijia.baseywmj.option.Config;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;


    public static final String WX_PAY_BORAD_COST_ACTION = "ywmj.com.client.wxpay.broadcost";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, Config.PAY.INSTANCE.getWX_APP_ID());
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

        Log.d("jam", "==req=");
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d("jam", "==resp=");
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int code = resp.errCode;
            if (code == 0) {
                payResult(true);
                //显示充值成功的页面和需要的操作
            } else {
                payResult(false);
            }

//            if (code == -1) {
//                //错误
//
//            }
//
//            if (code == -2) {
//                //用户取消
//            }

        }
    }


    private void payResult(boolean success) {
        Intent intent = new Intent();
        intent.setAction(WX_PAY_BORAD_COST_ACTION);
        intent.putExtra(Constant.INSTANCE.getPAY_SUCCESS_KEY(), success);
        sendBroadcast(intent);
        finish();
    }


}