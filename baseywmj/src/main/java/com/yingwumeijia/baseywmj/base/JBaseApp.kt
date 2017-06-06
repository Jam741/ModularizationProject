package com.yingwumeijia.baseywmj.base

import com.yingwumeijia.baseywmj.AppType
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.commonlibrary.base.BaseApplication

/**
 * Created by jamisonline on 2017/5/31.
 */
abstract class JBaseApp : BaseApplication() {

    abstract fun appType(): AppType

    override fun onCreate() {
        super.onCreate()
        AppTypeManager.setAppType(appType())
    }

}