package com.yingwumeijia.baseywmj.function.introduction.company

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView
import com.yingwumeijia.baseywmj.entity.bean.CaseBean

/**
 * Created by jamisonline on 2017/6/27.
 */
interface CompanyContract {

    interface View : JBaseView {

        fun showCompanyPortrait(url: String)

        fun showCompanyName(name: String)

        fun showDescribe(describe: String)

        fun showCompanyPic(picUrl: String ?)

        fun responseOtherCase(caseBeanList: List<CaseBean>)

        fun onLoadOtherComplete(pageNum: Int, empty: Boolean)

        fun showOtherCaseCount(count: Int)

        fun showHasResume(hasResume: Boolean)

        fun showIsCollected(isCollected: Boolean)

        fun initServiceStandard(serviceStandardDtoBean: CompanyIntriductionBean.ServiceStandardDtoBean)

    }


    interface Presenter : JBasePresenter {

        fun start()

        fun loadOtherCaseList(pageNum: Int)

        fun collect()

        fun cancelCollect()


    }
}