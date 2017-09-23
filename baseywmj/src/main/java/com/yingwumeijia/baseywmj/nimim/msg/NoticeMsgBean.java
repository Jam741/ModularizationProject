package com.yingwumeijia.baseywmj.nimim.msg;

/**
 * Created by jamisonline on 2017/9/22.
 */

public class NoticeMsgBean {

    /**
     * tipsType : 0
     * message : 这是测试的小灰条
     */

    private int tipsType;
    private String message;
    private String groupId;


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getTipsType() {
        return tipsType;
    }

    public void setTipsType(int tipsType) {
        this.tipsType = tipsType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
