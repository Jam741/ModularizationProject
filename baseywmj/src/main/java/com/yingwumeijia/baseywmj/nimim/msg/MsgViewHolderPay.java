package com.yingwumeijia.baseywmj.nimim.msg;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;
import com.yingwumeijia.baseywmj.R;
import com.yingwumeijia.baseywmj.api.Api;
import com.yingwumeijia.baseywmj.entity.bean.OrderBillPermissionDto;
import com.yingwumeijia.baseywmj.function.WebViewManager;
import com.yingwumeijia.baseywmj.utils.net.HttpUtil;
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber;

/**
 * Created by jamisonline on 2017/9/21.
 */

public class MsgViewHolderPay extends MsgViewHolderBase {


    TextView tv_title;
    TextView tvAmount;

    PayMessageAttachment payMessageAttachment;


    public MsgViewHolderPay(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.rc_item_payorder_message;
    }

    @Override
    protected void inflateContentView() {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tvAmount = (TextView) view.findViewById(R.id.tvAmount);
    }

    @Override
    protected void bindContentView() {
        payMessageAttachment = (PayMessageAttachment) message.getAttachment();
        tv_title.setText(payMessageAttachment.getTitle());
        tvAmount.setText("" + payMessageAttachment.getBillAmount());
    }

    @Override
    protected void onItemClick() {


        HttpUtil.getInstance().toNolifeSubscribe(Api.Companion.getService().checkBillPermission(payMessageAttachment.getBillId() + ""), new ProgressSubscriber<OrderBillPermissionDto>(view.getContext()) {
            @Override
            protected void _onNext(final OrderBillPermissionDto orderBillPermissionDto) {
                switch (orderBillPermissionDto.getPermissionType()) {
                    //（int，0 = 无权查看，1 = 可以查看直接跳转，2 = 订单已关闭可以查看，3 = 账单已取消不可以查看,
                    case 0:
                        new AlertDialog.Builder(view.getContext())
                                .setMessage("您当前无权查看此订单")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        break;
                    case 1:
                        WebViewManager.INSTANCE.startFullScreen(view.getContext(), orderBillPermissionDto.getUrl());
//                                OneWebActivity.start(view.getContext(), null, "http://192.168.28.115:8090/#/orderDetails?orderId=246&isCustomer=false&index=0&closeType=0", false);
                        break;
                    case 2:
                        new AlertDialog.Builder(view.getContext())
                                .setMessage("订单已关闭，点击“查看”进入订单详情")
                                .setPositiveButton("查看", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        WebViewManager.INSTANCE.startFullScreen(view.getContext(), orderBillPermissionDto.getUrl());
                                    }
                                }).show();

                        break;
                    case 3:
                        new AlertDialog.Builder(view.getContext())
                                .setMessage("账单已取消")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        break;
                }
            }
        });
    }

    @Override
    protected int leftBackground() {
        return R.mipmap.im_pay_bubble;
    }

    @Override
    protected int rightBackground() {
        return R.mipmap.im_pay_bubble;
    }
}
