package com.yingwumeijia.baseywmj.entity.bean;

import java.util.List;

/**
 * Created by Jam on 2017/3/25 下午11:14.
 * Describe:
 */

public class CollectUnreadResultBean {


    /**
     * totalCount : 0
     * currentPageNum : 0
     * totalPageNum : 0
     * previousUri :
     * currentUri :
     * nextUri :
     * result : [{"id":0,"caseId":0,"caseName":"","userId":0,"userType":"","userDetailType":"","available":false,"collectionTime":"","userShowName":"","userShowHead":"","userDetailTypeDesc":""}]
     */

    private int totalCount;
    private int currentPageNum;
    private int totalPageNum;
    private String previousUri;
    private String currentUri;
    private String nextUri;
    private List<ResultBean> result;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public String getPreviousUri() {
        return previousUri;
    }

    public void setPreviousUri(String previousUri) {
        this.previousUri = previousUri;
    }

    public String getCurrentUri() {
        return currentUri;
    }

    public void setCurrentUri(String currentUri) {
        this.currentUri = currentUri;
    }

    public String getNextUri() {
        return nextUri;
    }

    public void setNextUri(String nextUri) {
        this.nextUri = nextUri;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 0
         * caseId : 0
         * caseName :
         * userId : 0
         * userType :
         * userDetailType :
         * available : false
         * collectionTime :
         * userShowName :
         * userShowHead :
         * userDetailTypeDesc :
         */

        private int id;
        private int caseId;
        private String caseName;
        private int userId;
        private String userType;
        private String userDetailType;
        private boolean available;
        private String collectionTime;
        private String userShowName;
        private String userShowHead;
        private String userDetailTypeDesc;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCaseId() {
            return caseId;
        }

        public void setCaseId(int caseId) {
            this.caseId = caseId;
        }

        public String getCaseName() {
            return caseName;
        }

        public void setCaseName(String caseName) {
            this.caseName = caseName;
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

        public String getUserDetailType() {
            return userDetailType;
        }

        public void setUserDetailType(String userDetailType) {
            this.userDetailType = userDetailType;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public String getCollectionTime() {
            return collectionTime;
        }

        public void setCollectionTime(String collectionTime) {
            this.collectionTime = collectionTime;
        }

        public String getUserShowName() {
            return userShowName;
        }

        public void setUserShowName(String userShowName) {
            this.userShowName = userShowName;
        }

        public String getUserShowHead() {
            return userShowHead;
        }

        public void setUserShowHead(String userShowHead) {
            this.userShowHead = userShowHead;
        }

        public String getUserDetailTypeDesc() {
            return userDetailTypeDesc;
        }

        public void setUserDetailTypeDesc(String userDetailTypeDesc) {
            this.userDetailTypeDesc = userDetailTypeDesc;
        }
    }
}
