package com.yingwumeijia.baseywmj.function.casedetails.realscene;

import com.yingwumeijia.baseywmj.entity.bean.CaseBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Jam on 16/8/26 下午6:16.
 * Describe:
 */

public class RealSceneBean {


    /**
     * caseCover :
     * caseName :
     * pathOf720 :
     * cityName :
     * decorateStyle :
     * decorateType :
     * houseType :
     * houseArea : 0
     * totalCost : 0
     * buildingName :
     * scenes : [{"titleId":"","title":"","pics":[""],"explain":""}]
     * relativeCases : [{"caseId":0,"caseName":"","caseCover":""}]
     * designVideo : {"duration":0,"name":"","second":0,"url":""}
     * caseStory :
     */

    private String caseCover;
    private String caseName;
    private String pathOf720;
    private String cityName;
    private String decorateStyle;
    private String decorateType;
    private String houseType;
    private int houseArea;
    private BigDecimal totalCost;
    private String buildingName;
    private DesignVideoBean designVideo;
    private String caseStory;
    private List<ScenesBean> scenes;
    private List<CaseBean> relativeCases;
    private List<DesignPriceRangeDto> designPriceRangeDtos;
    private String hardPrice;
    private String softPrice;
    private int viewCount;
    private int collectionCount;
    private boolean supportedSupervisor;

    public boolean isSupportedSupervisor() {
        return supportedSupervisor;
    }

    public void setSupportedSupervisor(boolean supportedSupervisor) {
        this.supportedSupervisor = supportedSupervisor;
    }

    public List<DesignPriceRangeDto> getDesignPriceRangeDtos() {
        return designPriceRangeDtos;
    }

    public void setDesignPriceRangeDtos(List<DesignPriceRangeDto> designPriceRangeDtos) {
        this.designPriceRangeDtos = designPriceRangeDtos;
    }

    public String getHardPrice() {
        return hardPrice;
    }

    public void setHardPrice(String hardPrice) {
        this.hardPrice = hardPrice;
    }

    public String getSoftPrice() {
        return softPrice;
    }

    public void setSoftPrice(String softPrice) {
        this.softPrice = softPrice;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public String getCaseCover() {
        return caseCover;
    }

    public void setCaseCover(String caseCover) {
        this.caseCover = caseCover;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getPathOf720() {
        return pathOf720;
    }

    public void setPathOf720(String pathOf720) {
        this.pathOf720 = pathOf720;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDecorateStyle() {
        return decorateStyle;
    }

    public void setDecorateStyle(String decorateStyle) {
        this.decorateStyle = decorateStyle;
    }

    public String getDecorateType() {
        return decorateType;
    }

    public void setDecorateType(String decorateType) {
        this.decorateType = decorateType;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public int getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(int houseArea) {
        this.houseArea = houseArea;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public DesignVideoBean getDesignVideo() {
        return designVideo;
    }

    public void setDesignVideo(DesignVideoBean designVideo) {
        this.designVideo = designVideo;
    }

    public String getCaseStory() {
        return caseStory;
    }

    public void setCaseStory(String caseStory) {
        this.caseStory = caseStory;
    }

    public List<ScenesBean> getScenes() {
        return scenes;
    }

    public void setScenes(List<ScenesBean> scenes) {
        this.scenes = scenes;
    }

    public List<CaseBean> getRelativeCases() {
        return relativeCases;
    }

    public void setRelativeCases(List<CaseBean> relativeCases) {
        this.relativeCases = relativeCases;
    }


    public static class DesignPriceRangeDto {

        private int type;
        private String name;
        int priceStart;
        int priceEnd;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

    public static class DesignVideoBean {
        /**
         * duration : 0
         * name :
         * second : 0
         * url :
         */

        private int duration;
        private String name;
        private int second;
        private String url;

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSecond() {
            return second;
        }

        public void setSecond(int second) {
            this.second = second;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class ScenesBean {
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
