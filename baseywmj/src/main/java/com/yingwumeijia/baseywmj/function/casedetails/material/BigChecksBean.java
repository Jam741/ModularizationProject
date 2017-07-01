package com.yingwumeijia.baseywmj.function.casedetails.material;

import java.util.List;

/**
 * Created by jamisonline on 2017/6/27.
 */

public class BigChecksBean {

    List<CaseInfomationBean.ChecksBean> checksBeanList;

    String checksType;


    public BigChecksBean(List<CaseInfomationBean.ChecksBean> checksBeanList, String checksType) {
        this.checksBeanList = checksBeanList;
        this.checksType = checksType;
    }

    public List<CaseInfomationBean.ChecksBean> getChecksBeanList() {
        return checksBeanList;
    }

    public void setChecksBeanList(List<CaseInfomationBean.ChecksBean> checksBeanList) {
        this.checksBeanList = checksBeanList;
    }

    public String getChecksType() {
        return checksType;
    }

    public void setChecksType(String checksType) {
        this.checksType = checksType;
    }
}
