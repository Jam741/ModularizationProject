package com.yingwumeijia.baseywmj.entity.bean;

/**
 * Created by jamisonline on 2017/7/5.
 */

public class AdvisorInfoBean {


    /**
     * userId : 0
     * userDetailType :
     * name :
     * nickName :
     * headImage :
     * userPhone :
     * userDetailTypeDesc :
     */

    private int userId;
    private String userDetailType;
    private String name;
    private String nickName;
    private String headImage;
    private String userPhone;
    private String userDetailTypeDesc;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserDetailType() {
        return userDetailType;
    }

    public void setUserDetailType(String userDetailType) {
        this.userDetailType = userDetailType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserDetailTypeDesc() {
        return userDetailTypeDesc;
    }

    public void setUserDetailTypeDesc(String userDetailTypeDesc) {
        this.userDetailTypeDesc = userDetailTypeDesc;
    }
}
