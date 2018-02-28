package com.yingwumeijia.baseywmj.entity.bean;


/**
 * Created by Administrator on 2016/8/18.
 */
public class SeverBean {


    /**
     * serverUrl :
     * appImkey :
     * upgrade : false
     */
    String serverUrl;
    String webUrl;
    String appImkey;
    boolean upgrade;
    String latestAndroidUrl;
    String latestAndroidMd5;
    boolean newVersion;


    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getLatestAndroidUrl() {
        return latestAndroidUrl;
    }

    public void setLatestAndroidUrl(String latestAndroidUrl) {
        this.latestAndroidUrl = latestAndroidUrl;
    }

    public String getLatestAndroidMd5() {
        return latestAndroidMd5;
    }

    public void setLatestAndroidMd5(String latestAndroidMd5) {
        this.latestAndroidMd5 = latestAndroidMd5;
    }

    public boolean isNewVersion() {
        return newVersion;
    }

    public void setNewVersion(boolean newVersion) {
        this.newVersion = newVersion;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getAppImkey() {
        return appImkey;
    }

    public void setAppImkey(String appImkey) {
        this.appImkey = appImkey;
    }

    public boolean isUpgrade() {
        return upgrade;
    }

    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
    }
}
