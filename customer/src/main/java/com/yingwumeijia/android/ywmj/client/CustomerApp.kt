package com.yingwumeijia.android.ywmj.client

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.AppType
import com.yingwumeijia.baseywmj.base.JBaseApp

/**
 * Created by jamisonline on 2017/5/31.
 */
class CustomerApp : JBaseApp() {
    override fun appType(): AppType {
        return AppType.APP_C
    }

    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}