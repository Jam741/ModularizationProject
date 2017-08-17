package com.yingwumeijia.baseywmj.function.casedetails.realscene

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView
import com.yingwumeijia.baseywmj.entity.bean.CaseBean

/**
 * Created by jamisonline on 2017/6/26.
 */
interface RealSceneContract {


    interface View : JBaseView {

        fun clippingLayoutOf720()

        fun clippingLayoutOfVideo()

        fun init720Layout(has720: Boolean, caseCoverUrl: String)

        fun initBaseInfoLayout(baseInfo: String, caseName: String, caseStory: String)

        fun initRealPhotoLayout(scenes: List<RealSceneBean.ScenesBean>)

        fun initVideoLayout(videoBean: RealSceneBean.DesignVideoBean)

        fun initCaseListLayout(caseList: List<CaseBean>)

        fun initBaseInfoExtra(priceList: List<RealSceneBean.DesignPriceRangeDto>?, viewCount: Int, collectCount: Int)
    }

    interface Presenter : JBasePresenter {

        fun start()

        fun play720()
    }
}