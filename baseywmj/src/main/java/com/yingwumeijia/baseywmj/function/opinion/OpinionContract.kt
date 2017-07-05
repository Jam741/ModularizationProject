package com.yingwumeijia.baseywmj.function.opinion

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView
import java.util.ArrayList

/**
 * Created by jamisonline on 2017/7/5.
 */
interface OpinionContract {


    interface View : JBaseView {

        fun showSuccess()

    }

    interface Presenter : JBasePresenter {

        fun commit(images: ArrayList<String>, content: String)
    }
}