package com.yingwumeijia.baseywmj.function.extra;

import android.content.Context;

import com.yingwumeijia.baseywmj.api.Api;
import com.yingwumeijia.baseywmj.entity.bean.BillServiceInfo;
import com.yingwumeijia.baseywmj.entity.bean.BillSimpleInfo;
import com.yingwumeijia.baseywmj.entity.bean.PayBillInfo;
import com.yingwumeijia.baseywmj.utils.net.HttpUtil;
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Jam on 2017/3/21 下午3:20.
 * Describe:
 */

public class CreateBillServicePresenter implements CreateBillServicContract.Presenter {

    private CreateBillServicContract.View mView;
    private Context mContent;

    public CreateBillServicePresenter(CreateBillServicContract.View mView, Context mContent) {
        this.mView = mView;
        this.mContent = mContent;
    }



    @Override
    public void getBillService(String sessionId) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.Companion.getService().getBillServiceInfo(sessionId), new SimpleSubscriber<BillServiceInfo>(mContent) {
            @Override
            protected void _onNext(@Nullable BillServiceInfo billServiceInfo) {
                mView.initCreateService(billServiceInfo);
            }
        });
    }

    @Override
    public void checkBillSimple(String bill) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.Companion.getService().checkBillSimpleInfo(bill), new SimpleSubscriber<BillSimpleInfo>(mContent) {
            @Override
            protected void _onNext(@Nullable BillSimpleInfo billSimpleInfo) {
                mView.initBillSimple(billSimpleInfo);
            }
        });
    }

    @Override
    public void createPayBill(PayBillInfo payBillInfo) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.Companion.getService().createPayBill(payBillInfo), new SimpleSubscriber<String>(mContent) {
            @Override
            protected void _onNext(@Nullable String s) {
                mView.toCashier(s);
            }
        });
    }


}
