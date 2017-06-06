package com.yingwumeijia.commonlibrary.java

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Handler

import com.yingwumeijia.commonlibrary.utils.AppInfo


//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;

/**
 * 所有项目Application的父类

 * @author Jam
 * *         create at 2016/5/19 15:13
 */
class StarterApplication : Application() {

    //    public static RefWatcher getRefWatcher(Context context) {
    //        StarterApplication application = (StarterApplication) context.getApplicationContext();
    //        return application.refWatcher;
    //    }
    //
    //    private RefWatcher refWatcher;


    override fun onCreate() {
        super.onCreate()
        initialize()
    }


    private fun initialize() {
        //        refWatcher = LeakCanary.install(this);
        //       AndroidThreeTen.init(this);
        //        LeakCanary.install(this);
        //        refWatcher = installLeakCanary();
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
            private set
        @Volatile private var sAppHandler: Handler? = null
        @Volatile private var mAppInfo: AppInfo? = null

        //    protected RefWatcher installLeakCanary() {
        //        return RefWatcher.DISABLED;
        //    }

        fun appInfo(): AppInfo {
            if (mAppInfo == null) {
                mAppInfo = AppInfo(appContext())
            }
            return mAppInfo as AppInfo
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
