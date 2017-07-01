package com.yingwumeijia.baseywmj.function.casedetails.material

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView
import java.math.BigDecimal

/**
 * Created by jamisonline on 2017/6/27.
 */
interface MaterialContract {


    interface View : JBaseView {
        fun initCost(costs: List<CaseInfomationBean.CostsBean>, totallCost: BigDecimal, caseCover: String, totallArea: BigDecimal)

        fun initCostBrands(costBrands: List<CaseInfomationBean.CostBrandsBean>)

        /**
         * 户型改造

         * @param houseImages
         * *
         * @param designConcept
         */
        fun initDesignHouseImage(houseImages: List<CaseInfomationBean.DesignMaterialsBean.HouseImagesBean>)

        /**
         * @param displayImages
         * *
         * @param displayIllustration 陈列说明
         */
        fun initDesignDisplayImage(displayImages: List<CaseInfomationBean.DesignMaterialsBean.DisplayImagesBean>)


        /**
         * 施工验收

         * @param checksBeanList
         */
        fun initChecks(checksBeanList: List<BigChecksBean>)
    }


    interface Presenter : JBasePresenter {
        fun start()
    }
}