package com.yingwumeijia.baseywmj.base

import android.app.Activity
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


}