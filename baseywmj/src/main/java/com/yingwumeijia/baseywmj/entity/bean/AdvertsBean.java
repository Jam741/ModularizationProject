package com.yingwumeijia.baseywmj.entity.bean;

import java.io.Serializable;

/**
 * Created by jamisonline on 2017/8/2.
 */

public class AdvertsBean implements Serializable {


    /**
     * id : 0
     * name :
     * playTime : 0
     * advertsImage :
     */

    private int id;
    private String name;
    private int playTime;
    private String advertsImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public String getAdvertsImage() {
        return advertsImage;
    }

    public void setAdvertsImage(String advertsImage) {
        this.advertsImage = advertsImage;
    }
}
