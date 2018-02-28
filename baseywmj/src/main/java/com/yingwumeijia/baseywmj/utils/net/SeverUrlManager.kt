package com.yingwumeijia.baseywmj.utils.net


import android.content.Context
import com.yingwumeijia.baseywmj.option.PATHUrlConfig
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
    val KEY_IM_KEY = "KEY_IM_KEY"

    val DEFAULT_IM_KEY = "y745wfm84k0ov"


    fun baseUrl(): String {
        return SPUtils.get(BaseApplication.Companion.appContext(), SeverUrlManager.KEY_BASE_URL, PATHUrlConfig.DEFAULT_URL_C) as String
    }

    fun refreshBaseUrl(baseUrl: String) {

        SPUtils.put(BaseApplication.Companion.appContext(), SeverUrlManager.KEY_BASE_URL, baseUrl)
    }

    fun baseWebUrl(): String {
        return SPUtils.get(BaseApplication.Companion.appContext(), SeverUrlManager.KEY_BASE_WEB_URL, "") as String
    }

    fun refreshWebBaseUrl(baseUrl: String) {
        var webUrl: String
        if (baseUrl.contains("/appv/")) webUrl = baseUrl else webUrl = baseUrl + "/appv/"
        SPUtils.put(BaseApplication.Companion.appContext(), SeverUrlManager.KEY_BASE_WEB_URL, webUrl)
    }

    fun IMKey(): String {
        return SPUtils.get(BaseApplication.appContext(), KEY_IM_KEY, DEFAULT_IM_KEY) as String
    }

    fun refreshIMKey(imKey: String) {
        SPUtils.put(BaseApplication.appContext(), KEY_IM_KEY, imKey)
    }

}
