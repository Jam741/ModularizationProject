package com.yingwumeijia.baseywmj.function.message;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jam on 2016/11/20 下午10:00.
 * Describe:
 */

public class MessageBean implements Serializable {


    @SerializedName("id")
    int messageId;
    @SerializedName("type")
    String messageType;
    @SerializedName("title")
    String messageTitle;
    @SerializedName("content")
    String messageContetnt;
    @SerializedName("sendTime")
    String messageSendDate;
    String messageUserId;
    String messageSendId;
    @SerializedName("targetId")
    String messageTargetId;

    public String getMessageContetnt() {
        return messageContetnt;
    }

    public void setMessageContetnt(String messageContetnt) {
        this.messageContetnt = messageContetnt;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessageSendDate() {
        return messageSendDate;
    }

    public void setMessageSendDate(String messageSendDate) {
        this.messageSendDate = messageSendDate;
    }

    public String getMessageSendId() {
        return messageSendId;
    }

    public void setMessageSendId(String messageSendId) {
        this.messageSendId = messageSendId;
    }

    public String getMessageTargetId() {
        return messageTargetId;
    }

    public void setMessageTargetId(String messageTargetId) {
        this.messageTargetId = messageTargetId;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(String messageUserId) {
        this.messageUserId = messageUserId;
    }


}
