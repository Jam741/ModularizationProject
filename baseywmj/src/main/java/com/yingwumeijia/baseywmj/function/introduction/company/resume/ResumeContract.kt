package com.yingwumeijia.baseywmj.function.introduction.company.resume

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView
import java.util.ArrayList

/**
 * Created by jamisonline on 2017/6/28.
 */
interface ResumeContract {

    interface View : JBaseView {

        fun show720Preview(url: String)

        fun showVide0Preview(url: String)

        fun showPicList(picsBeen: List<CompanyResumeBean.PicsBean>)
    }


    interface Presenter : JBasePresenter {

        fun start()

        fun start720()

        fun playVideo()
    }
}