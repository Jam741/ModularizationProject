package com.yingwumeijia.baseywmj.entity.bean;

import java.util.List;

/**
 * Created by Jam on 2016/12/8 下午3:51.
 * Describe:
 */

public class SessionDetailBean {


    /**
     * sessionInfo : {"id":0,"userId":0,"userType":"","name":"","callTotal":0,"warning":false,"available":false,"closedUserId":0,"closedReason":"","createTime":"","updateTime":"","members":[{"userId":0,"userType":"","userJoinType":"","userDetailType":"","userTitle":"","showName":"","showHead":"","gender":"","phone":"","callTotal":0,"available":false,"companyId":0,"companyName":"","members":false,"imUid":"","creator":false,"servant":false}],"relatedCaseId":0}
     * relatedCaseInfo : {"id":0,"caseName":"","caseCover":"","status":"","collectionNumber":0}
     */

    private SessionInfoBean sessionInfo;
    private RelatedCaseInfoBean relatedCaseInfo;

    public SessionInfoBean getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfoBean sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public RelatedCaseInfoBean getRelatedCaseInfo() {
        return relatedCaseInfo;
    }

    public void setRelatedCaseInfo(RelatedCaseInfoBean relatedCaseInfo) {
        this.relatedCaseInfo = relatedCaseInfo;
    }

    public static class SessionInfoBean {
        /**
         * id : 0
         * userId : 0
         * userType :
         * name :
         * callTotal : 0
         * warning : false
         * available : false
         * closedUserId : 0
         * closedReason :
         * createTime :
         * updateTime :
         * members : [{"userId":0,"userType":"","userJoinType":"","userDetailType":"","userTitle":"","showName":"","showHead":"","gender":"","phone":"","callTotal":0,"available":false,"companyId":0,"companyName":"","members":false,"imUid":"","creator":false,"servant":false}]
         * relatedCaseId : 0
         */

        private int id;
        private int userId;
        private String userType;
        private String name;
        private int callTotal;
        private boolean warning;
        private boolean available;
        private int closedUserId;
        private String closedReason;
        private String createTime;
        private String updateTime;
        private int relatedCaseId;
        private List<MemberBean> members;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCallTotal() {
            return callTotal;
        }

        public void setCallTotal(int callTotal) {
            this.callTotal = callTotal;
        }

        public boolean isWarning() {
            return warning;
        }

        public void setWarning(boolean warning) {
            this.warning = warning;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public int getClosedUserId() {
            return closedUserId;
        }

        public void setClosedUserId(int closedUserId) {
            this.closedUserId = closedUserId;
        }

        public String getClosedReason() {
            return closedReason;
        }

        public void setClosedReason(String closedReason) {
            this.closedReason = closedReason;
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

        public int getRelatedCaseId() {
            return relatedCaseId;
        }

        public void setRelatedCaseId(int relatedCaseId) {
            this.relatedCaseId = relatedCaseId;
        }

        public List<MemberBean> getMembers() {
            return members;
        }

        public void setMembers(List<MemberBean> members) {
            this.members = members;
        }

    }

    public static class RelatedCaseInfoBean {
        /**
         * id : 0
         * caseName :
         * caseCover :
         * status :
         * collectionNumber : 0
         */

        private int id;
        private String caseName;
        private String caseCover;
        private String status;
        private int collectionNumber;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCaseName() {
            return caseName;
        }

        public void setCaseName(String caseName) {
            this.caseName = caseName;
        }

        public String getCaseCover() {
            return caseCover;
        }

        public void setCaseCover(String caseCover) {
            this.caseCover = caseCover;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getCollectionNumber() {
            return collectionNumber;
        }

        public void setCollectionNumber(int collectionNumber) {
            this.collectionNumber = collectionNumber;
        }
    }
}
