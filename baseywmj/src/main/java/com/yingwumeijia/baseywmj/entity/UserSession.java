package com.yingwumeijia.baseywmj.entity;

/**
 * Created by Jam on 2017/2/16 上午10:49.
 * Describe:
 */

public class UserSession {


    /**
     * sessionId :
     * accessToken :
     * refreshToken :
     */

    private String sessionId;
    private String accessToken;
    private String refreshToken;
    private String name;
    private String token;

    public UserSession() {
    }

    public UserSession(String sessionId, String accessToken, String name) {
        this.sessionId = sessionId;
        this.token = accessToken;
        this.name = name;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
