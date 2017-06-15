package com.yingwumeijia.commonlibrary.base

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.orhanobut.hawk.Hawk

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

        fun appResources(): Resources {
            return appContext().resources
        }
    }

}