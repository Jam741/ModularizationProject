package com.yingwumeijia.baseywmj.function.opinion;

import java.util.List;

/**
 * Created by Jam on 16/10/10 上午9:51.
 * Describe:
 */
public class FeedbackBean {


    /**
     * deviceId :
     * deviceOS :
     * deviceModel :
     * appVersion :
     * content :
     * images : [""]
     */

    private String deviceId;
    private String deviceOS;
    private String deviceModel;
    private String appVersion;
    private String content;
    private List<String> images;

    public FeedbackBean(String deviceId, String deviceOS, String deviceModel, String appVersion, String content, List<String> images) {
        this.deviceId = deviceId;
        this.deviceOS = deviceOS;
        this.deviceModel = deviceModel;
        this.appVersion = appVersion;
        this.content = content;
        this.images = images;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceOS() {
        return deviceOS;
    }

    public void setDeviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
