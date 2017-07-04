package com.yingwumeijia.baseywmj.function.personal

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView

/**
 * Created by jamisonline on 2017/6/11.
 */
interface PersonContract {


    interface View : JBaseView {

        fun didUpDateUserData()

        fun showMenus()

        fun showLoginView(logIn: Boolean)
    }


    interface Presenter : JBasePresenter {

        fun initPersonInfo()
    }
}