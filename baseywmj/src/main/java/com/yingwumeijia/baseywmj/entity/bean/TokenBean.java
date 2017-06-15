package com.yingwumeijia.baseywmj.entity.bean;


/**
 * Created by Jam on 16/7/18 下午2:14.
 * Describe:
 */
public class TokenBean {

    /**
     * userId : 7
     * userType : c
     * token : JVbl/PX+FwLpuW9zls86E3H8lKC65CPY4JyKiOpcZkXf9qsDdruZ+0oBkHew33y96cMKW0QT0wc=
     * imUid : c7
     */

    private int userId;
    private String userType;
    private String token;
    private String imUid;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImUid() {
        return imUid;
    }

    public void setImUid(String imUid) {
        this.imUid = imUid;
    }
}
