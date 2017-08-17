package com.yingwumeijia.baseywmj.entity.bean;

/**
 * Created by Jam on 2017/3/9 下午3:17.
 * Describe:
 */

public class CustomerDetailBean {


    /**
     * customerDto : {"id":0,"userName":"","password":"","userPhone":"","userType":"","available":false,"name":"","gender":"","age":"","mail":"","idCard":"","idImageA":"","idImageB":"","idHoldImage":"","idHoldImageB":"","createUserId":0,"createTime":"","updateTime":"","lastLoginTime":"","nickName":"","headImage":"","userSession":{"sessionId":"","accessToken":"","refreshToken":"","accessTokenExpired":false},"showName":"","showHead":""}
     * collectionCount : 0
     * twitterStatus :
     * twitterStatusDesc :
     * financialInfoUrl :
     */

    private UserBean customerDto;
    private UserBean employeeDto;
    private int collectionCount;
    private int twitterStatus = -1;
    private String twitterStatusDesc;
    private boolean isTwitterCustomer;
    private String twitterBaseUrl;
    private String financialInfoUrl;
    private boolean distributionStatus;
    private boolean homeAdvisorManager;

    public UserBean getEmployeeDto() {
        return employeeDto;
    }

    public void setEmployeeDto(UserBean employeeDto) {
        this.employeeDto = employeeDto;
    }

    public boolean isDistributionStatus() {
        return distributionStatus;
    }

    public void setDistributionStatus(boolean distributionStatus) {
        this.distributionStatus = distributionStatus;
    }

    public boolean isHomeAdvisorManager() {
        return homeAdvisorManager;
    }

    public void setHomeAdvisorManager(boolean homeAdvisorManager) {
        this.homeAdvisorManager = homeAdvisorManager;
    }

    public boolean isTwitterCustomer() {
        return isTwitterCustomer;
    }

    public void setTwitterCustomer(boolean twitterCustomer) {
        isTwitterCustomer = twitterCustomer;
    }

    public String getTwitterBaseUrl() {
        return twitterBaseUrl;
    }

    public void setTwitterBaseUrl(String twitterBaseUrl) {
        this.twitterBaseUrl = twitterBaseUrl;
    }

    public UserBean getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(UserBean customerDto) {
        this.customerDto = customerDto;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public int getTwitterStatus() {
        return twitterStatus;
    }

    public void setTwitterStatus(int twitterStatus) {
        this.twitterStatus = twitterStatus;
    }

    public String getTwitterStatusDesc() {
        return twitterStatusDesc;
    }

    public void setTwitterStatusDesc(String twitterStatusDesc) {
        this.twitterStatusDesc = twitterStatusDesc;
    }

    public String getFinancialInfoUrl() {
        return financialInfoUrl;
    }

    public void setFinancialInfoUrl(String financialInfoUrl) {
        this.financialInfoUrl = financialInfoUrl;
    }

    public static class CustomerDtoBean {
        /**
         * id : 0
         * userName :
         * password :
         * userPhone :
         * userType :
         * available : false
         * name :
         * gender :
         * age :
         * mail :
         * idCard :
         * idImageA :
         * idImageB :
         * idHoldImage :
         * idHoldImageB :
         * createUserId : 0
         * createTime :
         * updateTime :
         * lastLoginTime :
         * nickName :
         * headImage :
         * userSession : {"sessionId":"","accessToken":"","refreshToken":"","accessTokenExpired":false}
         * showName :
         * showHead :
         */

        private int id;
        private String userName;
        private String password;
        private String userPhone;
        private String userType;
        private boolean available;
        private String name;
        private String gender;
        private String age;
        private String mail;
        private String idCard;
        private String idImageA;
        private String idImageB;
        private String idHoldImage;
        private String idHoldImageB;
        private int createUserId;
        private String createTime;
        private String updateTime;
        private String lastLoginTime;
        private String nickName;
        private String headImage;
        private UserSessionBean userSession;
        private String showName;
        private String showHead;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getIdImageA() {
            return idImageA;
        }

        public void setIdImageA(String idImageA) {
            this.idImageA = idImageA;
        }

        public String getIdImageB() {
            return idImageB;
        }

        public void setIdImageB(String idImageB) {
            this.idImageB = idImageB;
        }

        public String getIdHoldImage() {
            return idHoldImage;
        }

        public void setIdHoldImage(String idHoldImage) {
            this.idHoldImage = idHoldImage;
        }

        public String getIdHoldImageB() {
            return idHoldImageB;
        }

        public void setIdHoldImageB(String idHoldImageB) {
            this.idHoldImageB = idHoldImageB;
        }

        public int getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(int createUserId) {
            this.createUserId = createUserId;
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

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
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

        public UserSessionBean getUserSession() {
            return userSession;
        }

        public void setUserSession(UserSessionBean userSession) {
            this.userSession = userSession;
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

        public static class UserSessionBean {
            /**
             * sessionId :
             * accessToken :
             * refreshToken :
             * accessTokenExpired : false
             */

            private String sessionId;
            private String accessToken;
            private String refreshToken;
            private boolean accessTokenExpired;

            public String getSessionId() {
                return sessionId;
            }

            public void setSessionId(String sessionId) {
                this.sessionId = sessionId;
            }

            public String getAccessToken() {
                return accessToken;
            }

            public void setAccessToken(String accessToken) {
                this.accessToken = accessToken;
            }

            public String getRefreshToken() {
                return refreshToken;
            }

            public void setRefreshToken(String refreshToken) {
                this.refreshToken = refreshToken;
            }

            public boolean isAccessTokenExpired() {
                return accessTokenExpired;
            }

            public void setAccessTokenExpired(boolean accessTokenExpired) {
                this.accessTokenExpired = accessTokenExpired;
            }
        }
    }
}
