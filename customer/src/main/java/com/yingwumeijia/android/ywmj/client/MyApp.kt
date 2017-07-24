package com.yingwumeijia.android.ywmj.client

import com.github.mzule.activityrouter.annotation.Modules
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.AppType
import com.yingwumeijia.baseywmj.base.JBaseApp
import com.yingwumeijia.sharelibrary.ShareSDK

/**
 * Created by jamisonline on 2017/5/31.
 */
@Modules("app", "sdk")
class MyApp : JBaseApp() {

    override fun appType(): AppType {
        return AppType.APP_C
    }

    override fun onCreate() {
        super.onCreate()
        ShareSDK.init(applicationContext)
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}