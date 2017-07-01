package com.yingwumeijia.baseywmj.function.setting

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView

/**
 * Created by jamisonline on 2017/6/30.
 */
interface SettingContract {


    interface View : JBaseView {

        fun showLoginOutButton(show: Boolean)

        fun showCurrentCache(cache: String)

    }

    interface Presenter : JBasePresenter {

        fun clearnCache()

        fun loginOut()

        fun start()

        fun resume()
    }
}