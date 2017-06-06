package com.yingwumeijia.baseywmj.base

import com.orhanobut.logger.Logger
import com.yingwumeijia.commonlibrary.base.BaseFragment

/**
 * Created by jamisonline on 2017/5/31.
 */
open class JBaseFragment : BaseFragment() {


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Logger.d("onHiddenChanged:" + hidden)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Logger.d("onLowMemory:" + this.javaClass.simpleName)
    }

    override fun onResume() {
        super.onResume()
        Logger.d("onResume")
    }


    override fun onPause() {
        super.onPause()
        Logger.d("onPause")
    }
}