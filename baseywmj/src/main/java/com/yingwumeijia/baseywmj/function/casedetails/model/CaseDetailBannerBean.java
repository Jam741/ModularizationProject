package com.yingwumeijia.baseywmj.function.casedetails.model;


import com.yingwumeijia.baseywmj.entity.bean.ShareBean;
import com.yingwumeijia.baseywmj.function.casedetails.model.BannerCaseInfoBean;

import java.util.List;

/**
 * Created by Jam on 16/8/29 下午6:11.
 * Describe:
 */
public class CaseDetailBannerBean {


    private List<PhasesBean> phases;

    /**
     * collected : false
     * hasChat : false
     * shareInfo : {"url":"","caseName":"","designConcept":"","cover":""}
     */
    private int bookingOrderId;//v1.3.0 预约订单id,

    private boolean collected;
    private long chatId = 0L;
    /**
     * url :
     * caseName :
     * designConcept :
     * cover :
     */
//
    private ShareBean shareInfo;

    private BannerCaseInfoBean statisticsVo;


    private String designerImage;
    private int commentTotalCount;

    public int getCommentTotalCount() {
        return commentTotalCount;
    }

    public void setCommentTotalCount(int commentTotalCount) {
        this.commentTotalCount = commentTotalCount;
    }

    public String getDesignerImage() {
        return designerImage;
    }

    public void setDesignerImage(String designerImage) {
        this.designerImage = designerImage;
    }

    public BannerCaseInfoBean getStatisticsVo() {
        return statisticsVo;
    }

    public void setStatisticsVo(BannerCaseInfoBean statisticsVo) {
        this.statisticsVo = statisticsVo;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public ShareBean getShareInfo() {
        return shareInfo;
    }

    public void setShareInfo(ShareBean shareInfo) {
        this.shareInfo = shareInfo;
    }

    public List<PhasesBean> getPhases() {
        return phases;
    }

    public void setPhases(List<PhasesBean> phases) {
        this.phases = phases;
    }

    public int getBookingOrderId() {
        return bookingOrderId;
    }

    public void setBookingOrderId(int bookingOrderId) {
        this.bookingOrderId = bookingOrderId;
    }

    public static class PhasesBean {
        private int code;
        private String desc;
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

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
    }


}
