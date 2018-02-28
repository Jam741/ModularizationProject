package com.yingwumeijia.baseywmj.function.web.jsbean;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jam on 2016/8/11 11:38.
 * Describe:
 */
public class ShareModel implements Serializable{

    private String mShareUrl;

    @SerializedName("Bitmap")
    private Bitmap mShareImg;

    @SerializedName("mShareImg")
    private String img;

    private String mDescription;

    private String mShareTitle;

    private String WX_APP_ID;


    public ShareModel(String mShareUrl, Bitmap mShareImg, String mDescription, String mShareTitle) {
        this.mShareUrl = mShareUrl;
        this.mShareImg = mShareImg;
        this.mDescription = mDescription;
        this.mShareTitle = mShareTitle;
    }

    public String getmShareUrl() {
        return mShareUrl;
    }

    public void setmShareUrl(String mShareUrl) {
        this.mShareUrl = mShareUrl;
    }

    public Bitmap getmShareImg() {
        return mShareImg;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setmShareImg(Bitmap mShareImg) {
        this.mShareImg = mShareImg;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmShareTitle() {
        return mShareTitle;
    }

    public void setmShareTitle(String mShareTitle) {
        this.mShareTitle = mShareTitle;
    }

    public String getWX_APP_ID() {
        return WX_APP_ID;
    }

    public void setWX_APP_ID(String WX_APP_ID) {
        this.WX_APP_ID = WX_APP_ID;
    }




}
