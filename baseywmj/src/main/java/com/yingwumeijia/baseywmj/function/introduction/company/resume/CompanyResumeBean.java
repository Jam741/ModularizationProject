package com.yingwumeijia.baseywmj.function.introduction.company.resume;

import java.util.List;

/**
 * Created by Jam on 16/9/2 下午2:57.
 * Describe:
 */
public class CompanyResumeBean {


    /**
     * cover :
     * pathOf720 :
     * video : {"duration":0,"name":"","second":0,"url":""}
     * pics : [{"title":"","pics":[""],"explain":""}]
     */

    private String cover;
    private String pathOf720;
    /**
     * duration : 0
     * name :
     * second : 0
     * url :
     */

    private VideoBean video;
    /**
     * title :
     * pics : [""]
     * explain :
     */

    private List<PicsBean> pics;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPathOf720() {
        return pathOf720;
    }

    public void setPathOf720(String pathOf720) {
        this.pathOf720 = pathOf720;
    }

    public VideoBean getVideo() {
        return video;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
    }

    public List<PicsBean> getPics() {
        return pics;
    }

    public void setPics(List<PicsBean> pics) {
        this.pics = pics;
    }


    public static class PicsBean {
        private String title;
        private String explain;
        private List<String> pics;

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
