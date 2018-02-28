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
        Log.d("jam", "==resp=" + resp.errCode);
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


//        //形参resp 有下面两个个属性比较重要
//        //1.resp.errCode
//        //2.resp.transaction则是在分享数据的时候手动指定的字符创,用来分辨是那次分享(参照4.中req.transaction)
//        switch (resp.errCode) { //根据需要的情况进行处理
//            case BaseResp.ErrCode.ERR_OK:
//                //正确返回
//                break;
//            case BaseResp.ErrCode.ERR_USER_CANCEL:
//                //用户取消
//                break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                //认证被否决
//                break;
//            case BaseResp.ErrCode.ERR_SENT_FAILED:
//                //发送失败
//                break;
//            case BaseResp.ErrCode.ERR_UNSUPPORT:
//                /不支持错误
//                break;
//            case BaseResp.ErrCode.ERR_COMM:
//                //一般错误
//                break;
//            default:
//                //其他不可名状的情况
//                break;
//        }
//
//        作者：吐痰高手
//        链接：http://www.jianshu.com/p/e1a3f6844079
//        來源：简书
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    }


    private void payResult(boolean success) {
        Intent intent = new Intent();
        intent.setAction(Constant.INSTANCE.getWX_PAY_BORAD_COST_ACTION());
        intent.putExtra(Constant.INSTANCE.getPAY_SUCCESS_KEY(), success);
        sendBroadcast(intent);
        finish();
    }


}