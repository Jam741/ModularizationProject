package com.yingwumeijia.baseywmj

import com.orhanobut.hawk.Hawk

/**
 * Created by jamisonline on 2017/6/5.
 */
object AppTypeManager {

    val KEY_APP_TYPE: String = "KEY_APP_TYPE"

    fun appType(): AppType {
        val appTypeStr = Hawk.get<String>(KEY_APP_TYPE, "APP_C")
        if (appTypeStr.equals("APP_C"))
            return AppType.APP_C
        else
            return AppType.APP_E
    }


    fun setAppType(appType: AppType) {
        when (appType) {
            AppType.APP_C -> Hawk.put(KEY_APP_TYPE, "APP_C")
            AppType.APP_E -> Hawk.put(KEY_APP_TYPE, "APP_E")
        }
    }

    fun isAppC(): Boolean {
        if (appType() == AppType.APP_C)
            return true
        return false
    }

}