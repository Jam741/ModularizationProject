package com.yingwumeijia.baseywmj.utils.net


import android.content.Context
import com.yingwumeijia.commonlibrary.BuildConfig
import com.yingwumeijia.commonlibrary.base.BaseApplication
import com.yingwumeijia.commonlibrary.utils.SPUtils

/**
 * Created by Jam on 2017/2/17 下午5:13.
 * Describe:
 */

object SeverUrlManager {


    val KEY_BASE_URL = "KEY_BASE_URL"
    val KEY_BASE_WEB_URL = "KEY_BASE_WEB_URL"

    val DEFAULT_URL_C = "https://customer.yingwumeijia.com/"

    val DEFAULT_URL_E = "https://employee.yingwumeijia.com/"


    fun baseUrl(): String {
        return com.yingwumeijia.commonlibrary.utils.SPUtils.get(com.yingwumeijia.commonlibrary.base.BaseApplication.Companion.appContext(), com.yingwumeijia.baseywmj.utils.net.SeverUrlManager.KEY_BASE_URL, com.yingwumeijia.baseywmj.utils.net.SeverUrlManager.DEFAULT_URL_C) as String
    }

    fun refreshBaseUrl(baseUrl: String) {
        com.yingwumeijia.commonlibrary.utils.SPUtils.put(com.yingwumeijia.commonlibrary.base.BaseApplication.Companion.appContext(), com.yingwumeijia.baseywmj.utils.net.SeverUrlManager.KEY_BASE_URL, baseUrl)
    }

    fun baseWebUrl(): String {
        return com.yingwumeijia.commonlibrary.utils.SPUtils.get(com.yingwumeijia.commonlibrary.base.BaseApplication.Companion.appContext(), com.yingwumeijia.baseywmj.utils.net.SeverUrlManager.KEY_BASE_WEB_URL, "") as String
    }

    fun refreshWebBaseUrl(baseUrl: String) {
        com.yingwumeijia.commonlibrary.utils.SPUtils.put(com.yingwumeijia.commonlibrary.base.BaseApplication.Companion.appContext(), com.yingwumeijia.baseywmj.utils.net.SeverUrlManager.KEY_BASE_WEB_URL, baseUrl)
    }


}
