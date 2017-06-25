package com.yingwumeijia.baseywmj.option

import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.BuildConfig
import com.yingwumeijia.commonlibrary.net.SeverUrlManager

/**
 * Created by jamisonline on 2017/6/15.
 *
 * 网络环境配置
 */
object PATHUrlConfig {

    /*编译环境配置，安全起见只对  debug包生效*/
    val buildPath = 2   //dev = 0 ,pre = 1 ,release = 2, test = 3 ,main_pc = 4

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

    /*本机地址*/
    val BASE_URL_H5_PC = "https://testmobile.yingwumeijia.com/appv/"


    fun severUrl(): String {
        if (BuildConfig.DEBUG) {
            Logger.d(BuildConfig.DEBUG)
            var url = ""
            when (buildPath) {
                0 -> url = BASE_URL_DEV
                1 -> url = BASE_URL_PRE
                2 -> url = BASE_URL_RELEASE
                3 -> url = BASE_URL_TEST
            }
            return url
        } else {
            return BASE_URL_DEV
        }
    }

    fun baseH5Url(): String {
        if (BuildConfig.DEBUG) {
            when (buildPath) {
                0 -> return BASE_URL_H5_DEV
                1 -> return BASE_URL_H5_PRE
                2 -> return BASE_URL_H5_RELEASE
                3 -> return BASE_URL_H5_TEST
                4 -> return BASE_URL_H5_PC
            }
        }
        return SeverUrlManager.baseWebUrl()
    }
}