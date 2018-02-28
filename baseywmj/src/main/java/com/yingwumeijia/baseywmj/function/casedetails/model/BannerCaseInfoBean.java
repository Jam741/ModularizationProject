package com.yingwumeijia.baseywmj.function.casedetails.model;

import java.util.List;

/**
 * Created by Jam on 2016/12/22 下午3:09.
 * Describe:
 */

public class BannerCaseInfoBean {


    /**
     * style :
     * styleStr :
     * layout :
     * layoutStr :
     * cost :
     * costStr :
     * types : [""]
     * typeStrs : [""]
     */

    private String style;
    private String styleStr;
    private String layout;
    private String layoutStr;
    private String cost;
    private String costStr;
    private int type;
    private List<String> types;
    private String typeStr;
    private String decorateType;
    private String decorateTypeStr;
    private String costRangeTypeStr;

    public String getCostRangeTypeStr() {
        return costRangeTypeStr;
    }

    public void setCostRangeTypeStr(String costRangeTypeStr) {
        this.costRangeTypeStr = costRangeTypeStr;
    }

    public String getDecorateTypeStr() {
        return decorateTypeStr;
    }

    public void setDecorateTypeStr(String decorateTypeStr) {
        this.decorateTypeStr = decorateTypeStr;
    }

    public String getDecorateType() {
        return decorateType;
    }

    public void setDecorateType(String decorateType) {
        this.decorateType = decorateType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleStr() {
        return styleStr;
    }

    public void setStyleStr(String styleStr) {
        this.styleStr = styleStr;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getLayoutStr() {
        return layoutStr;
    }

    public void setLayoutStr(String layoutStr) {
        this.layoutStr = layoutStr;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCostStr() {
        return costStr;
    }

    public void setCostStr(String costStr) {
        this.costStr = costStr;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }
}
