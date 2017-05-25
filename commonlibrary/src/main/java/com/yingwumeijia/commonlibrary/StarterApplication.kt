package com.yingwumeijia.commonlibrary

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Handler

import com.yingwumeijia.commonlibrary.utils.AppInfo


/**
 * 所有项目Application的父类

 * @author Jam
 * *         create at 2016/5/19 15:13
 */
class StarterApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        initialize()
    }


    private fun initialize() {

        instance = this
        sAppContent = applicationContext
        sAppHandler = Handler(sAppContent!!.mainLooper)
    }

    override fun onTerminate() {
        super.onTerminate()
        sAppContent = null
        instance = null
        sAppHandler = null
        mAppInfo = null
    }

    companion object {

        @Volatile private var sAppContent: Context? = null
        /**
         * current application instance
         */
        /**
         * @return current application instance
         */
        @Volatile var instance: StarterApplication? = null
        @Volatile private var sAppHandler: Handler? = null
        @Volatile private var mAppInfo: AppInfo? = null

        fun appInfo(): AppInfo {
            if (mAppInfo == null) {
                mAppInfo = AppInfo(appContext())
            }
            return mAppInfo!!
        }

        fun appContext(): Context {
            return sAppContent!!
        }

        fun appResources(): Resources {
            return appContext().resources
        }

        /**
         * application handler
         */
        fun appHandler(): Handler {
            return sAppHandler!!
        }
    }

}
