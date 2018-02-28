package com.yingwumeijia.baseywmj.function.collect.cases;

import java.util.List;

/**
 * Created by Jam on 2017/1/4 上午10:59.
 * Describe:
 */

public class CollectCaseBean {


    /**
     * totalCount : 0
     * currentPageNum : 0
     * totalPageNum : 0
     * previousUri :
     * currentUri :
     * nextUri :
     * result : [{"caseId":0,"caseName":"","caseCover":""}]
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
         * caseId : 0
         * caseName :
         * caseCover :
         */

        private int caseId;
        private String caseName;
        private String caseCover;

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

        public String getCaseCover() {
            return caseCover;
        }

        public void setCaseCover(String caseCover) {
            this.caseCover = caseCover;
        }
    }
}
