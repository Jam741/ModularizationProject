package com.yingwumeijia.commonlibrary.base

import android.app.Application
import android.content.Context
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogInterceptor
import com.orhanobut.hawk.NoEncryption
import com.orhanobut.logger.Logger

/**
 * Created by jamisonline on 2017/5/31.
 */
open class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        sAppContent = applicationContext

        Hawk.init(this)
                .build()
    }

    companion object {

        var sAppContent: Context? = null

        fun appContext(): Context {
            return sAppContent!!
        }
    }

}