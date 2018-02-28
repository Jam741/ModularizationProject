package com.yingwumeijia.baseywmj.function.introduction.employee

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView
import com.yingwumeijia.baseywmj.entity.bean.CaseBean

/**
 * Created by jamisonline on 2017/6/29.
 */
interface EmployeeContract {

    interface View : JBaseView {


        fun setEmployeePortrait(portraitUrl: String)

        fun showEmployeeName(name: String)

        fun showEmployeeJob(job: String)

        fun showCompanyName(company: String)

        fun showDescribe(describe: String)

        fun showEmployeePhoto(photo: ArrayList<String>)

        fun initEmployeePhotos(phontos: List<String>)

        fun showArticles(articlesBeanList: List<EmployeeIntroductionBean.ArticlesBean>)

        fun initServiceStandard(serviceStandardDtoBean: EmployeeIntroductionBean.ServiceStandardDtoBean)

        fun responseOtherCase(list: List<CaseBean>)

        fun loadOtherCaseComplete(pageNum:Int,empty:Boolean)

        fun showOtherCaseCount(count: Int)

        fun showIsCollected(isCollected: Boolean)

    }

    interface Presenter : JBasePresenter {

        fun start()

        fun loadOtherCase(page: Int)

        fun collect()

        fun cancelCollect()

    }
}