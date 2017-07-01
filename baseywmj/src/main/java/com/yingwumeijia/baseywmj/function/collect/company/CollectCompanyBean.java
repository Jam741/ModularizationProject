package com.yingwumeijia.baseywmj.function.collect.company;

import java.util.List;

/**
 * Created by Jam on 2017/1/4 上午10:47.
 * Describe:
 */

public class CollectCompanyBean {


    /**
     * totalCount : 0
     * currentPageNum : 0
     * totalPageNum : 0
     * previousUri :
     * currentUri :
     * nextUri :
     * result : [{"companyId":0,"companyName":"","companyHeadImage":"","majorBusiness":[""]}]
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
         * companyId : 0
         * companyName :
         * companyHeadImage :
         * majorBusiness : [""]
         *
         *
         *
         */

        private int companyId;
        private String companyName;
        private String companyHeadImage;
        private List<String> decorateTypes;

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyHeadImage() {
            return companyHeadImage;
        }

        public void setCompanyHeadImage(String companyHeadImage) {
            this.companyHeadImage = companyHeadImage;
        }

        public List<String> getDecorateTypes() {
            return decorateTypes;
        }

        public void setDecorateTypes(List<String> decorateTypes) {
            this.decorateTypes = decorateTypes;
        }
    }
}
