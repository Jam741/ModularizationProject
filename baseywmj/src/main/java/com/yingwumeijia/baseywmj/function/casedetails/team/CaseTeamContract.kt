package com.yingwumeijia.baseywmj.function.casedetails.team

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView

/**
 * Created by jamisonline on 2017/6/27.
 */
interface CaseTeamContract {


    interface View : JBaseView {

        fun showTeamList(teamData: ProductionTeamBean)

        fun showCeremonyPic(ceremonyBeanList: List<ProductionTeamBean.SurroundingMaterials.CeremonyBean>)

        fun supportMJProject(support: Boolean)

    }


    interface Presenter : JBasePresenter {
        fun start()
    }
}