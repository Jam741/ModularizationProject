package com.yingwumeijia.baseywmj.function.user.login;

/**
 * Created by Jam on 2016/12/20 下午2:53.
 * Describe:
 */

public class LoginBean {


    /**
     * identityInfo : {"phone":"","password":"","verifyCode":"","smsCode":""}
     * userExtensionInfo : {"equipmentNo":"","equipmentModel":"","systemInfo":"","netType":""}
     */

    private IdentityInfoBean identityInfo;
    private UserExtensionInfoBean userExtensionInfo;


    public LoginBean(IdentityInfoBean identityInfo, UserExtensionInfoBean userExtensionInfo) {
        this.identityInfo = identityInfo;
        this.userExtensionInfo = userExtensionInfo;
    }

    public IdentityInfoBean getIdentityInfo() {
        return identityInfo;
    }

    public void setIdentityInfo(IdentityInfoBean identityInfo) {
        this.identityInfo = identityInfo;
    }

    public UserExtensionInfoBean getUserExtensionInfo() {
        return userExtensionInfo;
    }

    public void setUserExtensionInfo(UserExtensionInfoBean userExtensionInfo) {
        this.userExtensionInfo = userExtensionInfo;
    }

    public static class IdentityInfoBean {
        /**
         * phone :
         * password :
         * verifyCode :
         * smsCode :
         */

        private String phone;
        private String password;
        private String verifyCode;
        private String smsCode;
        private String token;


        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getVerifyCode() {
            return verifyCode;
        }

        public void setVerifyCode(String verifyCode) {
            this.verifyCode = verifyCode;
        }

        public String getSmsCode() {
            return smsCode;
        }

        public void setSmsCode(String smsCode) {
            this.smsCode = smsCode;
        }
    }

    public static class UserExtensionInfoBean {
        /**
         * equipmentNo :
         * equipmentModel :
         * systemInfo :
         * netType :
         */

        private String equipmentNo;
        private String equipmentModel;
        private String systemInfo;
        private String netType;

        public String getEquipmentNo() {
            return equipmentNo;
        }

        public void setEquipmentNo(String equipmentNo) {
            this.equipmentNo = equipmentNo;
        }

        public String getEquipmentModel() {
            return equipmentModel;
        }

        public void setEquipmentModel(String equipmentModel) {
            this.equipmentModel = equipmentModel;
        }

        public String getSystemInfo() {
            return systemInfo;
        }

        public void setSystemInfo(String systemInfo) {
            this.systemInfo = systemInfo;
        }

        public String getNetType() {
            return netType;
        }

        public void setNetType(String netType) {
            this.netType = netType;
        }
    }
}
