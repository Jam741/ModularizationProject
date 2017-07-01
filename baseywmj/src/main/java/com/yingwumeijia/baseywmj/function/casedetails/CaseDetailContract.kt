package com.yingwumeijia.baseywmj.function.casedetails

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView

/**
 * Created by jamisonline on 2017/6/25.
 */
interface CaseDetailContract {


    interface View : JBaseView {

        fun setCollectStatus(isCollected: Boolean)

        fun showDesignerPortrait(portraitUrl: String)

        fun CommentCount(count: Int)

    }


    interface Presenter : JBasePresenter {


        fun start()

        fun collect()

        fun share()

        fun cancelCollect()

        fun connectTeam()
    }
}