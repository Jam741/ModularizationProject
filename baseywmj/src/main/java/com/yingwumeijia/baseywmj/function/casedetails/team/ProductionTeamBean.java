package com.yingwumeijia.baseywmj.function.casedetails.team;

import java.util.List;

/**
 * Created by Jam on 16/8/29 上午11:19.
 * Describe:
 */
public class ProductionTeamBean {


    /**
     * employees : [{"userId":0,"name":"","title":"","employeeDetailType":"","headImage":"","companyId":0,"companyName":""}]
     * company : {"companyId":0,"companyName":"","companyHeadImage":"","decorateTypes":[""]}
     */

    private CompanyBean company;
    private List<EmployeesBean> employees;
    private SurroundingMaterials surroundingMaterials;

    public SurroundingMaterials getSurroundingMaterials() {
        return surroundingMaterials;
    }

    public void setSurroundingMaterials(SurroundingMaterials surroundingMaterials) {
        this.surroundingMaterials = surroundingMaterials;
    }

    public CompanyBean getCompany() {
        return company;
    }

    public void setCompany(CompanyBean company) {
        this.company = company;
    }

    public List<EmployeesBean> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeesBean> employees) {
        this.employees = employees;
    }

    public static class CompanyBean {
        /**
         * companyId : 0
         * companyName :
         * companyHeadImage :
         * decorateTypes : [""]
         */

        private int companyId;
        private String companyName;
        private String companyHeadImage;
        private List<String> decorateTypes;
        private boolean supportedSupervisor;

        public boolean isSupportedSupervisor() {
            return supportedSupervisor;
        }

        public void setSupportedSupervisor(boolean supportedSupervisor) {
            this.supportedSupervisor = supportedSupervisor;
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

        public String getCompanyHeadImage() {
            return companyHeadImage;
        }

        public void setCompanyHeadImage(String companyHeadImage) {
            this.companyHeadImage = companyHeadImage;
        }

        public List<String> getDecorateTypes() {
            return decorateTypes;
        }

        public void setDecorateTypes(List<String> decorateTypes) {
            this.decorateTypes = decorateTypes;
        }
    }

    public static class EmployeesBean {
        /**
         * userId : 0
         * name :
         * title :
         * employeeDetailType :
         * headImage :
         * companyId : 0
         * companyName :
         */

        private int userId;
        private String name;
        private String title;
        private String employeeDetailType;
        private String headImage;
        private int companyId;
        private String companyName;

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

        public String getEmployeeDetailType() {
            return employeeDetailType;
        }

        public void setEmployeeDetailType(String employeeDetailType) {
            this.employeeDetailType = employeeDetailType;
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
    }


    public static class SurroundingMaterials {


        /**
         * startCeremony : {"titleId":"","title":"","pics":[""],"explain":""}
         * endCeremony : {"titleId":"","title":"","pics":[""],"explain":""}
         */

        private CeremonyBean startCeremony;
        private CeremonyBean endCeremony;
        private String startDate;
        private String endDate;

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public CeremonyBean getStartCeremony() {
            return startCeremony;
        }

        public void setStartCeremony(CeremonyBean startCeremony) {
            this.startCeremony = startCeremony;
        }

        public CeremonyBean getEndCeremony() {
            return endCeremony;
        }

        public void setEndCeremony(CeremonyBean endCeremony) {
            this.endCeremony = endCeremony;
        }

        public static class CeremonyBean {
            /**
             * titleId :
             * title :
             * pics : [""]
             * explain :
             */

            private String titleId;
            private String title;
            private String explain;
            private List<String> pics;

            public String getTitleId() {
                return titleId;
            }

            public void setTitleId(String titleId) {
                this.titleId = titleId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getExplain() {
                return explain;
            }

            public void setExplain(String explain) {
                this.explain = explain;
            }

            public List<String> getPics() {
                return pics;
            }

            public void setPics(List<String> pics) {
                this.pics = pics;
            }
        }
    }
}
