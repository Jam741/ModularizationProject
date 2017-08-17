package com.yingwumeijia.baseywmj.entity.bean;

import java.util.List;

/**
 * Created by Jam on 2017/3/2 下午7:06.
 * Describe:
 */

public class CommentResultBean {


    /**
     * totalCount : 0
     * currentPageNum : 0
     * totalPageNum : 0
     * previousUri :
     * currentUri :
     * nextUri :
     * result : [{"id":0,"userId":0,"userType":"","userDetailType":"","userShowName":"","userShowHead":"","content":"","createTime":"","replyList":[{"id":0,"employeeUserId":0,"employeeDetailType":"","employeeShowName":"","employeeShowHead":"","content":"","createTime":""}]}]
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
         * userId : 0
         * userType :
         * userDetailType :
         * userShowName :
         * userShowHead :
         * content :
         * createTime :
         * replyList : [{"id":0,"employeeUserId":0,"employeeDetailType":"","employeeShowName":"","employeeShowHead":"","content":"","createTime":""}]
         */

        private int id;
        private int userId;
        private String caseName;
        private String userType;
        private String userDetailType;
        private String userShowName;
        private String userShowHead;
        private String content;
        private String createTime;
        private List<ReplyListBean> replyList;

        public String getCaseName() {
            return caseName;
        }

        public void setCaseName(String caseName) {
            this.caseName = caseName;
        }

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

        public String getUserDetailType() {
            return userDetailType;
        }

        public void setUserDetailType(String userDetailType) {
            this.userDetailType = userDetailType;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<ReplyListBean> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<ReplyListBean> replyList) {
            this.replyList = replyList;
        }

        public static class ReplyListBean {
            /**
             * id : 0
             * employeeUserId : 0
             * employeeDetailType :
             * employeeShowName :
             * employeeShowHead :
             * content :
             * createTime :
             */

            private int id;
            private int employeeUserId;
            private String employeeDetailType;
            private String employeeShowName;
            private String employeeShowHead;
            private String content;
            private String createTime;
            private String employeeDetailTypeDesc;

            public String getEmployeeDetailTypeDesc() {
                return employeeDetailTypeDesc;
            }

            public void setEmployeeDetailTypeDesc(String employeeDetailTypeDesc) {
                this.employeeDetailTypeDesc = employeeDetailTypeDesc;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getEmployeeUserId() {
                return employeeUserId;
            }

            public void setEmployeeUserId(int employeeUserId) {
                this.employeeUserId = employeeUserId;
            }

            public String getEmployeeDetailType() {
                return employeeDetailType;
            }

            public void setEmployeeDetailType(String employeeDetailType) {
                this.employeeDetailType = employeeDetailType;
            }

            public String getEmployeeShowName() {
                return employeeShowName;
            }

            public void setEmployeeShowName(String employeeShowName) {
                this.employeeShowName = employeeShowName;
            }

            public String getEmployeeShowHead() {
                return employeeShowHead;
            }

            public void setEmployeeShowHead(String employeeShowHead) {
                this.employeeShowHead = employeeShowHead;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}


