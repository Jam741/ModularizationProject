package com.yingwumeijia.baseywmj.base

import android.app.Activity
import android.content.Context
import android.util.Log
import com.tencent.smtt.sdk.QbSdk
import com.yingwumeijia.baseywmj.AppType
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.commonlibrary.base.BaseApplication
import java.util.HashMap

/**
 * Created by jamisonline on 2017/5/31.
 */
abstract class JBaseApp : BaseApplication() {

    abstract fun appType(): AppType


    override fun onCreate() {
        super.onCreate()
        initTBSWebView()
        AppTypeManager.setAppType(appType())
    }


    companion object {
        /**
         * 保存当前正在使用的会话页面的实例，用于在解散回话后，关闭该实例

         * @param activity
         */

        private val currentActivitysContainer by lazy { HashMap<String, Activity>() }

        fun setCurrentConversation(activity: Activity) {
            currentActivitysContainer.put("KEY_CONVERSATION_ACTIVITY", activity)
        }


        fun finishCurrentConversation() {
            if (currentActivitysContainer.containsKey("KEY_CONVERSATION_ACTIVITY")) {
                currentActivitysContainer["KEY_CONVERSATION_ACTIVITY"]!!.finish()
            }
        }
    }


    fun initTBSWebView() {

        val preInitCallback: QbSdk.PreInitCallback = object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                Log.d("jam", " onCoreInitFinished is ")
            }

            override fun onViewInitFinished(p0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("jam", " onViewInitFinished is " + p0)
            }
        }
        QbSdk.initX5Environment(applicationContext, preInitCallback)

    }


}