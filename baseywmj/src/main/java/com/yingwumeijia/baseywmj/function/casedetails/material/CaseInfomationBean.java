package com.yingwumeijia.baseywmj.function.casedetails.material;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Jam on 2017/2/28 上午11:59.
 * Describe:
 */

public class CaseInfomationBean {


    /**
     * totalPrice : 0
     * costs : [{"id":"","name":"","cost":0}]
     * costBrands : [{"costInfo":{"id":"","name":"","cost":0},"brands":[{"id":0,"name":"","logo":"","website":"","available":false,"brandClasses":[{"id":0,"brandId":0,"type":0,"subType":0,"subTypeName":"","typeName":""}]}]}]
     * hardChecks : [{"phaseName":"","phaseDescription":"","videos":[{"duration":0,"name":"","second":0,"url":""}],"images":[{"titleId":"","title":"","pics":[""],"explain":""}]}]
     * softChecks : [{"phaseName":"","phaseDescription":"","videos":[{"duration":0,"name":"","second":0,"url":""}],"images":[{"titleId":"","title":"","pics":[""],"explain":""}]}]
     * designMaterials : {"houseImages":[{"originHouseImage":{"titleId":"","title":"","pics":[""],"explain":""},"designHouseImage":{"titleId":"","title":"","pics":[""],"explain":""},"remakeIllustration":""}],"displayImages":[{"titleId":"","title":"","pics":[""],"explain":""}],"displayIllustration":"","designConcept":"","designVideo":{"duration":0,"name":"","second":0,"url":""}}
     */

    private String caseCover;

    private BigDecimal houseArea;

    public BigDecimal getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(BigDecimal houseArea) {
        this.houseArea = houseArea;
    }

    public String getCaseCover() {
        return caseCover;
    }

    public void setCaseCover(String caseCover) {
        this.caseCover = caseCover;
    }

    private BigDecimal totalPrice;
    private DesignMaterialsBean designMaterials;
    private List<CostsBean> costs;
    private List<CostBrandsBean> costBrands;
    private List<ChecksBean> hardChecks;
    private List<ChecksBean> softChecks;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public DesignMaterialsBean getDesignMaterials() {
        return designMaterials;
    }

    public void setDesignMaterials(DesignMaterialsBean designMaterials) {
        this.designMaterials = designMaterials;
    }

    public List<CostsBean> getCosts() {
        return costs;
    }

    public void setCosts(List<CostsBean> costs) {
        this.costs = costs;
    }

    public List<CostBrandsBean> getCostBrands() {
        return costBrands;
    }

    public void setCostBrands(List<CostBrandsBean> costBrands) {
        this.costBrands = costBrands;
    }

    public List<ChecksBean> getHardChecks() {
        return hardChecks;
    }

    public void setHardChecks(List<ChecksBean> hardChecks) {
        this.hardChecks = hardChecks;
    }

    public List<ChecksBean> getSoftChecks() {
        return softChecks;
    }

    public void setSoftChecks(List<ChecksBean> softChecks) {
        this.softChecks = softChecks;
    }

    public static class DesignMaterialsBean {
        /**
         * houseImages : [{"originHouseImage":{"titleId":"","title":"","pics":[""],"explain":""},"designHouseImage":{"titleId":"","title":"","pics":[""],"explain":""},"remakeIllustration":""}]
         * displayImages : [{"titleId":"","title":"","pics":[""],"explain":""}]
         * displayIllustration :
         * designConcept :
         * designVideo : {"duration":0,"name":"","second":0,"url":""}
         */

        private String displayIllustration;
        private String designConcept;
        private String totalRemakeIllustration;
        private DesignVideoBean designVideo;
        private List<HouseImagesBean> houseImages;
        private List<DisplayImagesBean> displayImages;

        public String getTotalRemakeIllustration() {
            return totalRemakeIllustration;
        }

        public void setTotalRemakeIllustration(String totalRemakeIllustration) {
            this.totalRemakeIllustration = totalRemakeIllustration;
        }

        public String getDisplayIllustration() {
            return displayIllustration;
        }

        public void setDisplayIllustration(String displayIllustration) {
            this.displayIllustration = displayIllustration;
        }

        public String getDesignConcept() {
            return designConcept;
        }

        public void setDesignConcept(String designConcept) {
            this.designConcept = designConcept;
        }

        public DesignVideoBean getDesignVideo() {
            return designVideo;
        }

        public void setDesignVideo(DesignVideoBean designVideo) {
            this.designVideo = designVideo;
        }

        public List<HouseImagesBean> getHouseImages() {
            return houseImages;
        }

        public void setHouseImages(List<HouseImagesBean> houseImages) {
            this.houseImages = houseImages;
        }

        public List<DisplayImagesBean> getDisplayImages() {
            return displayImages;
        }

        public void setDisplayImages(List<DisplayImagesBean> displayImages) {
            this.displayImages = displayImages;
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

        public static class HouseImagesBean {
            /**
             * originHouseImage : {"titleId":"","title":"","pics":[""],"explain":""}
             * designHouseImage : {"titleId":"","title":"","pics":[""],"explain":""}
             * remakeIllustration :
             */

            private OriginHouseImageBean originHouseImage;
            private DesignHouseImageBean designHouseImage;
            private String remakeIllustration;

            public OriginHouseImageBean getOriginHouseImage() {
                return originHouseImage;
            }

            public void setOriginHouseImage(OriginHouseImageBean originHouseImage) {
                this.originHouseImage = originHouseImage;
            }

            public DesignHouseImageBean getDesignHouseImage() {
                return designHouseImage;
            }

            public void setDesignHouseImage(DesignHouseImageBean designHouseImage) {
                this.designHouseImage = designHouseImage;
            }

            public String getRemakeIllustration() {
                return remakeIllustration;
            }

            public void setRemakeIllustration(String remakeIllustration) {
                this.remakeIllustration = remakeIllustration;
            }

            public static class OriginHouseImageBean {
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

            public static class DesignHouseImageBean {
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

        public static class DisplayImagesBean {
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

    public static class CostsBean {
        /**
         * id :
         * name :
         * cost : 0
         */

        private int id;
        private String name;
        private BigDecimal cost;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getCost() {
            return cost;
        }

        public void setCost(BigDecimal cost) {
            this.cost = cost;
        }
    }

    public static class CostBrandsBean {
        /**
         * costInfo : {"id":"","name":"","cost":0}
         * brands : [{"id":0,"name":"","logo":"","website":"","available":false,"brandClasses":[{"id":0,"brandId":0,"type":0,"subType":0,"subTypeName":"","typeName":""}]}]
         */

        private CostsBean costInfo;
        private List<BrandsBean> brands;

        public CostsBean getCostInfo() {
            return costInfo;
        }

        public void setCostInfo(CostsBean costInfo) {
            this.costInfo = costInfo;
        }

        public List<BrandsBean> getBrands() {
            return brands;
        }

        public void setBrands(List<BrandsBean> brands) {
            this.brands = brands;
        }


        public static class BrandsBean {
            /**
             * id : 0
             * name :
             * logo :
             * website :
             * available : false
             * brandClasses : [{"id":0,"brandId":0,"type":0,"subType":0,"subTypeName":"","typeName":""}]
             */

            private int id;
            private String name;
            private String logo;
            private String website;
            private boolean available;
            private List<BrandClassesBean> brandClasses;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getWebsite() {
                return website;
            }

            public void setWebsite(String website) {
                this.website = website;
            }

            public boolean isAvailable() {
                return available;
            }

            public void setAvailable(boolean available) {
                this.available = available;
            }

            public List<BrandClassesBean> getBrandClasses() {
                return brandClasses;
            }

            public void setBrandClasses(List<BrandClassesBean> brandClasses) {
                this.brandClasses = brandClasses;
            }

            public static class BrandClassesBean {
                /**
                 * id : 0
                 * brandId : 0
                 * type : 0
                 * subType : 0
                 * subTypeName :
                 * typeName :
                 */

                private int id;
                private int brandId;
                private int type;
                private int subType;
                private String subTypeName;
                private String typeName;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getBrandId() {
                    return brandId;
                }

                public void setBrandId(int brandId) {
                    this.brandId = brandId;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getSubType() {
                    return subType;
                }

                public void setSubType(int subType) {
                    this.subType = subType;
                }

                public String getSubTypeName() {
                    return subTypeName;
                }

                public void setSubTypeName(String subTypeName) {
                    this.subTypeName = subTypeName;
                }

                public String getTypeName() {
                    return typeName;
                }

                public void setTypeName(String typeName) {
                    this.typeName = typeName;
                }
            }
        }
    }

    public static class ChecksBean implements Parcelable {


        /**
         * phaseName :
         * phaseDescription :
         * videos : [{"duration":0,"name":"","second":0,"url":""}]
         * images : [{"titleId":"","title":"","pics":[""],"explain":""}]
         */

        private String phaseName;
        private String checksType;
        private String phaseDescription;
        private List<VideosBean> videos;
        private List<ImagesBean> images;

        public String getChecksType() {
            return checksType;
        }

        public void setChecksType(String checksType) {
            this.checksType = checksType;
        }

        protected ChecksBean(Parcel in) {
            phaseName = in.readString();
            phaseDescription = in.readString();
        }

        public static final Creator<ChecksBean> CREATOR = new Creator<ChecksBean>() {
            @Override
            public ChecksBean createFromParcel(Parcel in) {
                return new ChecksBean(in);
            }

            @Override
            public ChecksBean[] newArray(int size) {
                return new ChecksBean[size];
            }
        };

        public String getPhaseName() {
            return phaseName;
        }

        public void setPhaseName(String phaseName) {
            this.phaseName = phaseName;
        }

        public String getPhaseDescription() {
            return phaseDescription;
        }

        public void setPhaseDescription(String phaseDescription) {
            this.phaseDescription = phaseDescription;
        }

        public List<VideosBean> getVideos() {
            return videos;
        }

        public void setVideos(List<VideosBean> videos) {
            this.videos = videos;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(phaseName);
            dest.writeString(phaseDescription);
        }

        public static class VideosBean implements Parcelable {
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
            private String description;

            protected VideosBean(Parcel in) {
                duration = in.readInt();
                name = in.readString();
                second = in.readInt();
                url = in.readString();
                description = in.readString();
            }

            public static final Creator<VideosBean> CREATOR = new Creator<VideosBean>() {
                @Override
                public VideosBean createFromParcel(Parcel in) {
                    return new VideosBean(in);
                }

                @Override
                public VideosBean[] newArray(int size) {
                    return new VideosBean[size];
                }
            };

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(duration);
                dest.writeString(name);
                dest.writeInt(second);
                dest.writeString(url);
                dest.writeString(description);
            }
        }

        public static class ImagesBean implements Parcelable {
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

            protected ImagesBean(Parcel in) {
                titleId = in.readString();
                title = in.readString();
                explain = in.readString();
                pics = in.createStringArrayList();
            }

            public static final Creator<ImagesBean> CREATOR = new Creator<ImagesBean>() {
                @Override
                public ImagesBean createFromParcel(Parcel in) {
                    return new ImagesBean(in);
                }

                @Override
                public ImagesBean[] newArray(int size) {
                    return new ImagesBean[size];
                }
            };

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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(titleId);
                dest.writeString(title);
                dest.writeString(explain);
                dest.writeStringList(pics);
            }
        }
    }

}

