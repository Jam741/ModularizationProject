package com.yingwumeijia.baseywmj.entity.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Jam on 16/6/15 上午10:30.
 * Describe:
 */
public class CaseBean implements Serializable {

    private int caseId;
    private String caseName;
    private String caseCover;
    private String style;
    private String houseType;
    private int houseArea;
    private BigDecimal cost;
    private String decorateType;
    private int collectionCount;
    private boolean has720;
    private long viewCount;

    public int getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(int houseArea) {
        this.houseArea = houseArea;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseCover() {
        return caseCover;
    }

    public void setCaseCover(String caseCover) {
        this.caseCover = caseCover;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getDecorateType() {
        return decorateType;
    }

    public void setDecorateType(String decorateType) {
        this.decorateType = decorateType;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public boolean isHas720() {
        return has720;
    }

    public void setHas720(boolean has720) {
        this.has720 = has720;
    }
}
