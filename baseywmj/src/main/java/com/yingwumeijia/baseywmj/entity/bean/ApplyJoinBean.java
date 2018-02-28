package com.yingwumeijia.baseywmj.entity.bean;

/**
 * Created by Jam on 2017/2/24 上午11:49.
 * Describe:
 */

public class ApplyJoinBean {


    /**
     * companyName :
     * companyAddr :
     * areaId : 0
     * contactName :
     * contactPhone :
     * smsCode :
     */

    private String companyName;
    private String companyAddr;
    private int areaId;
    private String contactName;
    private String contactPhone;
    private String smsCode;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
