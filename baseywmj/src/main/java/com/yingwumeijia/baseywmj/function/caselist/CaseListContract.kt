package com.yingwumeijia.baseywmj.function.caselist

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView
import com.yingwumeijia.baseywmj.entity.bean.CaseBean

/**
 * Created by jamisonline on 2017/6/4.
 */

interface CaseListContract {


    interface View : JBaseView {

        fun onResponseList(list: ArrayList<CaseBean>)

        fun showNavLayoutBar(show: Boolean)

        fun showGoTopBotton(show: Boolean)

        fun showEmpty(empty: Boolean)

        fun onLoadComplete(page: Int, empty: Boolean)

    }


    interface Presenter : JBasePresenter {

        fun loadCaseList(page: Int, caseFilterOptionBody: CaseFilterOptionBody)

    }


}
