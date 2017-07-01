package com.yingwumeijia.baseywmj.function.introduction.company;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jam on 16/9/2 上午10:43.
 * Describe:
 */
public class CompanyIntriductionBean {


    /**
     * companyId : 0
     * logo :
     * name :
     * resume :
     * cover :
     * otherCasesCount : 0
     * hasPresent : false
     * isCollected : false
     * serviceAreas : [""]
     * serviceStandardDto : {"serviceStandards":[{"serviceType":"","serviceName":"","selected":false,"serviceDesc":"","priceStart":0,"priceEnd":0}],"decorateTypes":[""]}
     */

    private int companyId;
    private String logo;
    private String name;
    private String resume;
    private String cover;
    @SerializedName("casesCount")
    private int otherCasesCount = 0;
    private boolean hasPresent;
    private boolean isCollected;
    private ServiceStandardDtoBean serviceStandardDto;
    private List<String> serviceAreas;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getOtherCasesCount() {
        return otherCasesCount;
    }

    public void setOtherCasesCount(int otherCasesCount) {
        this.otherCasesCount = otherCasesCount;
    }

    public boolean isHasPresent() {
        return hasPresent;
    }

    public void setHasPresent(boolean hasPresent) {
        this.hasPresent = hasPresent;
    }

    public boolean isIsCollected() {
        return isCollected;
    }

    public void setIsCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }

    public ServiceStandardDtoBean getServiceStandardDto() {
        return serviceStandardDto;
    }

    public void setServiceStandardDto(ServiceStandardDtoBean serviceStandardDto) {
        this.serviceStandardDto = serviceStandardDto;
    }

    public List<String> getServiceAreas() {
        return serviceAreas;
    }

    public void setServiceAreas(List<String> serviceAreas) {
        this.serviceAreas = serviceAreas;
    }

    public static class ServiceStandardDtoBean {
        private List<ServiceStandardsBean> serviceStandards;
        private List<String> decorateTypes;

        public List<ServiceStandardsBean> getServiceStandards() {
            return serviceStandards;
        }

        public void setServiceStandards(List<ServiceStandardsBean> serviceStandards) {
            this.serviceStandards = serviceStandards;
        }

        public List<String> getDecorateTypes() {
            return decorateTypes;
        }

        public void setDecorateTypes(List<String> decorateTypes) {
            this.decorateTypes = decorateTypes;
        }

        public static class ServiceStandardsBean {
            /**
             * serviceType :
             * serviceName :
             * selected : false
             * serviceDesc :
             * priceStart : 0
             * priceEnd : 0
             */

            private int serviceType;
            private String serviceName;
            private boolean selected;
            private String serviceDesc;
            private int priceStart;
            private int priceEnd;

            public int getServiceType() {
                return serviceType;
            }

            public void setServiceType(int serviceType) {
                this.serviceType = serviceType;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public boolean isSelected() {
                return selected;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }

            public String getServiceDesc() {
                return serviceDesc;
            }

            public void setServiceDesc(String serviceDesc) {
                this.serviceDesc = serviceDesc;
            }

            public int getPriceStart() {
                return priceStart;
            }

            public void setPriceStart(int priceStart) {
                this.priceStart = priceStart;
            }

            public int getPriceEnd() {
                return priceEnd;
            }

            public void setPriceEnd(int priceEnd) {
                this.priceEnd = priceEnd;
            }
        }
    }
}
