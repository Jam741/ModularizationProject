package com.yingwumeijia.baseywmj.option

import com.yingwumeijia.baseywmj.BuildConfig

/**
 * Created by jamisonline on 2017/6/15.
 */
object BaseUrlConfig {

    val DEFAULT_URL_C = "https://customer.yingwumeijia.com/"

    val DEFAULT_URL_E = "https://employee.yingwumeijia.com/"


    /*生产环境*/
    val BASE_URL_RELEASE = "https://gate.yingwumeijia.com/"
    val BASE_URL_H5_RELEASE = "https://mobile.yingwumeijia.com/appv/"
    /*预发布环境*/
    val BASE_URL_PRE = "https://pregate.yingwumeijia.com/"
    val BASE_URL_H5_PRE = "https://premobile.yingwumeijia.com/appv/"
    /*开发环境*/
    val BASE_URL_DEV = "https://devgate.yingwumeijia.com"
    val BASE_URL_H5_DEV = "https://devmobile.yingwumeijia.com/appv/"
    /*测试环境*/
    val BASE_URL_TEST = "https://testgate.yingwumeijia.com"
    val BASE_URL_H5_TEST = "https://testmobile.yingwumeijia.com/appv/"


    fun baseSeverUrl(): String {
        if (BuildConfig.DEBUG) {
            if (BuildConfig.PATH.equals("dev")) {
                return BASE_URL_DEV
            } else if (BuildConfig.PATH.equals("test")) {
                return BASE_URL_TEST
            } else if (BuildConfig.PATH.equals("pre"))
                return BASE_URL_PRE
        }
        return BASE_URL_RELEASE
    }

    fun baseH5Url(): String {
        if (BuildConfig.DEBUG) {
            if (BuildConfig.PATH.equals("dev")) {
                return BASE_URL_H5_DEV
            } else if (BuildConfig.PATH.equals("test")) {
                return BASE_URL_H5_TEST
            } else if (BuildConfig.PATH.equals("pre"))
                return BASE_URL_H5_PRE
        }
        return BASE_URL_H5_RELEASE
    }
}