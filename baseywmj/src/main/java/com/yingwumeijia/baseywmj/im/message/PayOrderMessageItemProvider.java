package com.yingwumeijia.baseywmj.im.message;


/**
 * Created by Jam on 2017/3/21 下午6:36.
 * Describe:
 */

//@ProviderTag(messageContent = PayOrderMessageContent.class)
public class PayOrderMessageItemProvider  {

//    @Override
//    public void bindView(View view, int i, PayOrderMessageContent payMessageContent, UIMessage uiMessage) {
//        ViewHolder holder = (ViewHolder) view.getTag();
//
//
//        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {
//            holder.pay_message_layout.setBackgroundResource(R.drawable.chat_to_pay_back_bubble);
//        } else {
//            holder.pay_message_layout.setBackgroundResource(R.drawable.chat_to_pay_bubble);
//        }
//
//        holder.tv_title.setText(payMessageContent.getContent());
//        holder.tv_amount.setText(payMessageContent.getBillAmount());
//    }
//
//    @Override
//    public Spannable getContentSummary(PayOrderMessageContent payMessageContent) {
//        return null;
//    }
//
//    @Override
//    public void onItemClick(final View view, int i, final PayOrderMessageContent payMessageContent, UIMessage uiMessage) {
//
//        HttpUtil.getInstance().toNolifeSubscribe(Api.Companion.getService().checkBillPermission(payMessageContent.getBillId()), new ProgressSubscriber<OrderBillPermissionDto>(view.getContext()) {
//            @Override
//            protected void _onNext(final OrderBillPermissionDto orderBillPermissionDto) {
//                switch (orderBillPermissionDto.getPermissionType()) {
//                    //（int，0 = 无权查看，1 = 可以查看直接跳转，2 = 订单已关闭可以查看，3 = 账单已取消不可以查看,
//                    case 0:
//                        new AlertDialog.Builder(view.getContext())
//                                .setMessage("您当前无权查看此订单")
//                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                }).show();
//                        break;
//                    case 1:
//                        WebViewManager.INSTANCE.startFullScreen(view.getContext(), orderBillPermissionDto.getUrl());
////                                OneWebActivity.start(view.getContext(), null, "http://192.168.28.115:8090/#/orderDetails?orderId=246&isCustomer=false&index=0&closeType=0", false);
//                        break;
//                    case 2:
//                        new AlertDialog.Builder(view.getContext())
//                                .setMessage("订单已关闭，点击“查看”进入订单详情")
//                                .setPositiveButton("查看", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        WebViewManager.INSTANCE.startFullScreen(view.getContext(), orderBillPermissionDto.getUrl());
//                                    }
//                                }).show();
//
//                        break;
//                    case 3:
//                        new AlertDialog.Builder(view.getContext())
//                                .setMessage("账单已取消")
//                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                }).show();
//                        break;
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onItemLongClick(View view, int i, PayOrderMessageContent payMessageContent, UIMessage uiMessage) {
//
//    }
//
//    class ViewHolder {
//        LinearLayout pay_message_layout;
//        TextView tv_title;
//        TextView tv_amount;
//    }
//
//
//    @Override
//    public View newView(Context context, ViewGroup viewGroup) {
//        View view = LayoutInflater.from(context).inflate(R.layout.rc_item_payorder_message, null);
//        ViewHolder holder = new ViewHolder();
//        holder.pay_message_layout = (LinearLayout) view.findViewById(R.id.pay_message_layout);
//        holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
//        holder.tv_amount = (TextView) view.findViewById(R.id.tv_amount);
//        view.setTag(holder);
//        return view;
//    }

}
