package com.yingwumeijia.baseywmj.entity

import android.support.annotation.IdRes
import android.support.v7.widget.DialogTitle
import com.flyco.tablayout.listener.CustomTabEntity

/**
 * Created by jamisonline on 2017/5/31.
 */
class TabEntity constructor(var title: String, @IdRes var selectedIcon: Int, @IdRes var unSelectedIcon: Int) : CustomTabEntity {

    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }


}