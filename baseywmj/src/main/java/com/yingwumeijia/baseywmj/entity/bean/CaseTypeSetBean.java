package com.yingwumeijia.baseywmj.entity.bean;

import java.util.List;

/**
 * Created by jamisonline on 2017/6/12.
 */

public class CaseTypeSetBean {


    List<CaseTypeEnum> doneCasesTypes;
    List<CaseTypeEnum> designCasesTypes;
    List<CaseTypeEnum> houseType;
    List<CaseTypeEnum> houseAreaTypes;
    List<CaseTypeEnum> decorateStyleType;
    List<CaseTypeEnum> decorateTypes;
    List<CaseTypeEnum> cityTypes;


    public List<CaseTypeEnum> getDoneCasesTypes() {
        return doneCasesTypes;
    }

    public void setDoneCasesTypes(List<CaseTypeEnum> doneCasesTypes) {
        this.doneCasesTypes = doneCasesTypes;
    }

    public List<CaseTypeEnum> getDesignCasesTypes() {
        return designCasesTypes;
    }

    public void setDesignCasesTypes(List<CaseTypeEnum> designCasesTypes) {
        this.designCasesTypes = designCasesTypes;
    }

    public List<CaseTypeEnum> getCityTypes() {
        return cityTypes;
    }

    public void setCityTypes(List<CaseTypeEnum> cityTypes) {
        this.cityTypes = cityTypes;
    }

    public List<CaseTypeEnum> getDecorateStyleType() {
        return decorateStyleType;
    }

    public void setDecorateStyleType(List<CaseTypeEnum> decorateStyleType) {
        this.decorateStyleType = decorateStyleType;
    }

    public List<CaseTypeEnum> getHouseType() {
        return houseType;
    }

    public void setHouseType(List<CaseTypeEnum> houseType) {
        this.houseType = houseType;
    }

    public List<CaseTypeEnum> getHouseAreaTypes() {
        return houseAreaTypes;
    }

    public void setHouseAreaTypes(List<CaseTypeEnum> houseAreaTypes) {
        this.houseAreaTypes = houseAreaTypes;
    }

    public List<CaseTypeEnum> getDecorateTypes() {
        return decorateTypes;
    }

    public void setDecorateTypes(List<CaseTypeEnum> decorateTypes) {
        this.decorateTypes = decorateTypes;
    }
}
