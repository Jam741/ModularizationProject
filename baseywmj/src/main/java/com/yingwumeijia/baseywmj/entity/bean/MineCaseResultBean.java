package com.yingwumeijia.baseywmj.entity.bean;

import java.util.List;

/**
 * Created by Jam on 2017/3/3 下午4:41.
 * Describe:
 */

public class MineCaseResultBean {


    /**
     * totalCount : 0
     * currentPageNum : 0
     * totalPageNum : 0
     * previousUri :
     * currentUri :
     * nextUri :
     * result : [{"id":0,"caseName":"","caseCover":"","companyId":0,"status":"","visitNumber":0,"collectionNumber":0,"commentNumber":0,"unreadCommentNumber":0}]
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
         * caseName :
         * caseCover :
         * companyId : 0
         * status :
         * visitNumber : 0
         * collectionNumber : 0
         * commentNumber : 0
         * unreadCommentNumber : 0
         */

        private int id;
        private String caseName;
        private String caseCover;
        private int companyId;
        private String status;
        private int visitNumber;
        private int collectionNumber;
        private int commentNumber;
        private int unreadCommentNumber;

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

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getVisitNumber() {
            return visitNumber;
        }

        public void setVisitNumber(int visitNumber) {
            this.visitNumber = visitNumber;
        }

        public int getCollectionNumber() {
            return collectionNumber;
        }

        public void setCollectionNumber(int collectionNumber) {
            this.collectionNumber = collectionNumber;
        }

        public int getCommentNumber() {
            return commentNumber;
        }

        public void setCommentNumber(int commentNumber) {
            this.commentNumber = commentNumber;
        }

        public int getUnreadCommentNumber() {
            return unreadCommentNumber;
        }

        public void setUnreadCommentNumber(int unreadCommentNumber) {
            this.unreadCommentNumber = unreadCommentNumber;
        }
    }
}
