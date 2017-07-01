package com.yingwumeijia.baseywmj.function.introduction.company.resume;

import java.io.Serializable;

/**
 * Created by Jam on 16/9/2 下午4:04.
 * Describe:
 */
public class VideoBean implements Serializable {

    /**
     * duration : 0
     * name :
     * second : 0
     * url :
     */

    private int duration;
    private String name;
    private int second;
    private String url;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
