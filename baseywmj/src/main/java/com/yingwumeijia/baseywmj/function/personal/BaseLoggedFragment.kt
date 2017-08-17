package com.yingwumeijia.baseywmj.function.personal

import com.yingwumeijia.baseywmj.base.JBaseFragment

/**
 * Created by jamisonline on 2017/6/30.
 */
abstract class BaseLoggedFragment : JBaseFragment() {

    /**
     * 用户数据改变
     */
    abstract fun onUserDataChanged()


    abstract fun showDistributionStatus(show:Boolean)

}