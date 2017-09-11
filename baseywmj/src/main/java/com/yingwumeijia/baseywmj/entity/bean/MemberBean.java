package com.yingwumeijia.baseywmj.entity.bean;

import java.io.Serializable;

/**
 * Created by Jam on 2016/12/8 下午12:00.
 * Describe:
 */

public class MemberBean implements Serializable{


    /**
     * userId : 0
     * userType :
     * userDetailType :
     * userTitle :
     * showName :
     * showHead :
     * companyId : 0
     * gender :
     * phone :
     * createTime :
     * updateTime :
     * available : false
     * imUid :
     */

//
//
//    {
//        "userId": 0,
//            "userType": "",
//            "userJoinType": "",
//            "userDetailType": "",
//            "userTitle": "",
//            "showName": "",
//            "showHead": "",
//            "gender": "",
//            "phone": "",
//            "callTotal": 0,
//            "available": false,
//            "companyId": 0,
//            "companyName": "",
//            "members": false,
//            "imUid": "",
//            "creator": false,
//            "servant": false
//    }



    private int userId;
    private String userType;
    private String userJoinType;
    private int userDetailType;
    private String userTitle;
    private String showName;
    private String showHead;
    private int companyId;
    private int callTotal;
    private String companyName;
    private String gender;
    private String phone;
    private String createTime;
    private String updateTime;
    private boolean members;
    private boolean creator;
    private boolean servant;
    private boolean available;
    private String imUid;
    private boolean isSelected;
    private boolean joinSession;

    public boolean isJoinSession() {
        return joinSession;
    }

    public void setJoinSession(boolean joinSession) {
        this.joinSession = joinSession;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getCallTotal() {
        return callTotal;
    }

    public void setCallTotal(int callTotal) {
        this.callTotal = callTotal;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isCreator() {
        return creator;
    }

    public void setCreator(boolean creator) {
        this.creator = creator;
    }

    public boolean isMembers() {
        return members;
    }

    public void setMembers(boolean members) {
        this.members = members;
    }

    public boolean isServant() {
        return servant;
    }

    public void setServant(boolean servant) {
        this.servant = servant;
    }

    public String getUserJoinType() {
        return userJoinType;
    }

    public void setUserJoinType(String userJoinType) {
        this.userJoinType = userJoinType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getUserDetailType() {
        return userDetailType;
    }

    public void setUserDetailType(int userDetailType) {
        this.userDetailType = userDetailType;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowHead() {
        return showHead;
    }

    public void setShowHead(String showHead) {
        this.showHead = showHead;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getImUid() {
        return imUid;
    }

    public void setImUid(String imUid) {
        this.imUid = imUid;
    }
}
