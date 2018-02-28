package com.yingwumeijia.baseywmj.entity.bean;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/8/15.
 */

public class ShareBean {

    /**
     * url :
     * caseName :
     * designConcept :
     * cover :
     */

    private String url;
    private String caseName;
    @SerializedName("caseStory")
    private String designConcept;
    private String cover;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getDesignConcept() {
        return designConcept;
    }

    public void setDesignConcept(String designConcept) {
        this.designConcept = designConcept;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
