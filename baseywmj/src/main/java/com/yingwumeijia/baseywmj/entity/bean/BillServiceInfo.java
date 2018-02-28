package com.yingwumeijia.baseywmj.entity.bean;

import java.util.List;

/**
 * Created by Jam on 2017/3/22 上午11:36.
 * Describe:
 */

public class BillServiceInfo {


    /**
     * companyId : 0
     * companyName :
     * companyLogo :
     * billServices : [{"code":"","desc":"","count":0}]
     */

    private int companyId;
    private String companyName;
    private String companyLogo;
    private List<BillServicesBean> billServices;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public List<BillServicesBean> getBillServices() {
        return billServices;
    }

    public void setBillServices(List<BillServicesBean> billServices) {
        this.billServices = billServices;
    }

    public static class BillServicesBean {
        /**
         * code :
         * desc :
         * count : 0
         */

        private int code;
        private String desc;
        private int count;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
