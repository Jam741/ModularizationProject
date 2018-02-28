package com.yingwumeijia.baseywmj.function.introduction.serviceStandard;

import java.util.List;

/**
 * Created by Jam on 2017/3/2 下午3:47.
 * Describe:
 */

public class ServiceStandardBean {


    /**
     * sourceType :
     * employeeVo : {"userId":0,"name":"","title":"","headImage":"","companyId":0,"companyName":"","employeeDetailType":""}
     * service : {"procedureDesc":"","priceRangeStart":0,"priceRangeEnd":0,"items":[{"title":"","description":"","costType":"","costValue":0,"serviceContents":[{"id":0,"type":"","name":"","description":"","createTime":"","updateTime":""}],"otherDemands":""}]}
     */

    private String sourceType;
    private EmployeeVoBean employeeVo;
    private ServiceBean service;

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public EmployeeVoBean getEmployeeVo() {
        return employeeVo;
    }

    public void setEmployeeVo(EmployeeVoBean employeeVo) {
        this.employeeVo = employeeVo;
    }

    public ServiceBean getService() {
        return service;
    }

    public void setService(ServiceBean service) {
        this.service = service;
    }

    public static class EmployeeVoBean {
        /**
         * userId : 0
         * name :
         * title :
         * headImage :
         * companyId : 0
         * companyName :
         * employeeDetailType :
         */

        private int userId;
        private String name;
        private String title;
        private String headImage;
        private int companyId;
        private String companyName;
        private String employeeDetailType;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
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

        public String getEmployeeDetailType() {
            return employeeDetailType;
        }

        public void setEmployeeDetailType(String employeeDetailType) {
            this.employeeDetailType = employeeDetailType;
        }
    }

    public static class ServiceBean {
        /**
         * procedureDesc :
         * priceRangeStart : 0
         * priceRangeEnd : 0
         * items : [{"title":"","description":"","costType":"","costValue":0,"serviceContents":[{"id":0,"type":"","name":"","description":"","createTime":"","updateTime":""}],"otherDemands":""}]
         */

        private String procedureDesc;
        private int priceRangeStart;
        private int priceRangeEnd;
        private List<ItemsBean> items;

        public String getProcedureDesc() {
            return procedureDesc;
        }

        public void setProcedureDesc(String procedureDesc) {
            this.procedureDesc = procedureDesc;
        }

        public int getPriceRangeStart() {
            return priceRangeStart;
        }

        public void setPriceRangeStart(int priceRangeStart) {
            this.priceRangeStart = priceRangeStart;
        }

        public int getPriceRangeEnd() {
            return priceRangeEnd;
        }

        public void setPriceRangeEnd(int priceRangeEnd) {
            this.priceRangeEnd = priceRangeEnd;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * title :
             * description :
             * costType :
             * costValue : 0
             * serviceContents : [{"id":0,"type":"","name":"","description":"","createTime":"","updateTime":""}]
             * otherDemands :
             */

            private String title;
            private String description;
            private int costType;
            private int costValue;
            private String otherDemands;
            private List<ServiceContentsBean> serviceContents;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getCostType() {
                return costType;
            }

            public void setCostType(int costType) {
                this.costType = costType;
            }

            public int getCostValue() {
                return costValue;
            }

            public void setCostValue(int costValue) {
                this.costValue = costValue;
            }

            public String getOtherDemands() {
                return otherDemands;
            }

            public void setOtherDemands(String otherDemands) {
                this.otherDemands = otherDemands;
            }

            public List<ServiceContentsBean> getServiceContents() {
                return serviceContents;
            }

            public void setServiceContents(List<ServiceContentsBean> serviceContents) {
                this.serviceContents = serviceContents;
            }

            public static class ServiceContentsBean {
                /**
                 * id : 0
                 * type :
                 * name :
                 * description :
                 * createTime :
                 * updateTime :
                 */

                private int id;
                private String type;
                private String name;
                private String description;
                private String createTime;
                private String updateTime;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }
            }
        }
    }
}
