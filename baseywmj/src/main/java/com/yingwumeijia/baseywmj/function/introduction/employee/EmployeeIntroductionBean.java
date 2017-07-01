package com.yingwumeijia.baseywmj.function.introduction.employee;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jam on 16/9/1 下午2:40.
 * Describe:
 */
public class EmployeeIntroductionBean {


    /**
     * userId : 0
     * headImage :
     * name :
     * title :
     * companyId : 0
     * companyName :
     * resume :
     * presentPics : [""]
     * articles : [{"title":"","thumb":"","url":""}]
     * otherCasesCount : 0
     * isCollected : false
     * serviceAreas : [""]
     * serviceStandardDto : {"serviceStandards":[{"serviceType":"","serviceName":"","selected":false,"serviceDesc":"","priceStart":0,"priceEnd":0}],"decorateTypes":[""]}
     * collected : false
     */

    private int userId;
    private String headImage;
    private String name;
    private String title;
    private int companyId;
    private String companyName;
    private String resume;
    @SerializedName("casesCount")
    private int otherCasesCount;
    private boolean isCollected;
    private ServiceStandardDtoBean serviceStandardDto;
    private boolean collected;
    private List<String> presentPics;
    private List<ArticlesBean> articles;
    private List<String> serviceAreas;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public int getOtherCasesCount() {
        return otherCasesCount;
    }

    public void setOtherCasesCount(int otherCasesCount) {
        this.otherCasesCount = otherCasesCount;
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

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public List<String> getPresentPics() {
        return presentPics;
    }

    public void setPresentPics(List<String> presentPics) {
        this.presentPics = presentPics;
    }

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
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

    public static class ArticlesBean {
        /**
         * title :
         * thumb :
         * url :
         */

        private String title;
        private String thumb;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
