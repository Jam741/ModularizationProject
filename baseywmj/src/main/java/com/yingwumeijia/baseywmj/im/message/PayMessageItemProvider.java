package com.yingwumeijia.baseywmj.im.message;

import android.content.Context;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yingwumeijia.baseywmj.R;
import com.yingwumeijia.commonlibrary.utils.TextViewUtils;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

/**
 * Created by Jam on 2017/3/21 下午6:36.
 * Describe:
 */

@ProviderTag(messageContent = PayMessageContent.class)
public class PayMessageItemProvider extends IContainerItemProvider.MessageProvider<PayMessageContent> {

    @Override
    public void bindView(View view, int i, PayMessageContent payMessageContent, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();


        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {
            holder.pay_message_layout.setBackgroundResource(R.drawable.chat_to_pay_back_bubble);
            TextViewUtils.setDrawableToLeft(view.getContext(), holder.tv_content, R.mipmap.im_pay_bubble_ic);
        } else {
            holder.pay_message_layout.setBackgroundResource(R.drawable.chat_to_pay_bubble);
            TextViewUtils.setDrawableToLeft(view.getContext(), holder.tv_content, R.mipmap.im_pay_bubble_ic);
        }

        holder.tv_content.setText(payMessageContent.getContent());
    }

    @Override
    public Spannable getContentSummary(PayMessageContent payMessageContent) {
        return null;
    }

    @Override
    public void onItemClick(View view, int i, PayMessageContent payMessageContent, UIMessage uiMessage) {
        //TODO :   CreateBillServiceActivity.start(view.getContext(), payMessageContent.getBillId(), uiMessage.getTargetId(),payMessageContent.getExtra());
    }

    @Override
    public void onItemLongClick(View view, int i, PayMessageContent payMessageContent, UIMessage uiMessage) {

    }

    class ViewHolder {
        LinearLayout pay_message_layout;
        TextView tv_content;
    }


    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_item_payment_message, null);
        ViewHolder holder = new ViewHolder();
        holder.pay_message_layout = (LinearLayout) view.findViewById(R.id.pay_message_layout);
        holder.tv_content = (TextView) view.findViewById(R.id.tv_content);
        view.setTag(holder);
        return view;
    }

}
