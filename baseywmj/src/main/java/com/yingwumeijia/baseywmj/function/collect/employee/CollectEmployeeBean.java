package com.yingwumeijia.baseywmj.function.collect.employee;

import java.util.List;

/**
 * Created by Jam on 2017/1/4 上午10:23.
 * Describe:
 */

public class CollectEmployeeBean {


    /**
     * totalCount : 0
     * currentPageNum : 0
     * totalPageNum : 0
     * previousUri :
     * currentUri :
     * nextUri :
     * result : [{"userId":0,"name":"","title":"","headImage":"","companyName":""}]
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
         * userId : 0
         * name :
         * title :
         * headImage :
         * companyName :
         */

        private int userId;
        private String name;
        private String title;
        private String headImage;
        private String companyName;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
    }
}
