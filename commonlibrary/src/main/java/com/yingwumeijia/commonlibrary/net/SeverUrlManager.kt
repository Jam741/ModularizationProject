package com.yingwumeijia.commonlibrary.net


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

    val DEFAULT_URL_C = "https://customer.yingwumeijia.com/"

    val DEFAULT_URL_E = "https://employee.yingwumeijia.com/"


    fun baseUrl(): String {
        return SPUtils.get(BaseApplication.appContext(), KEY_BASE_URL, "") as String
    }

    fun refreshBaseUrl(baseUrl: String) {
        SPUtils.put(BaseApplication.appContext(), KEY_BASE_URL, baseUrl)
    }


}
