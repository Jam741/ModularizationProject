package com.yingwumeijia.baseywmj.option

import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.BuildConfig
import com.yingwumeijia.baseywmj.utils.net.SeverUrlManager

/**
 * Created by jamisonline on 2017/6/15.
 *
 * 网络环境配置
 */
object PATHUrlConfig {

    /*编译环境配置，安全起见只对  debug包生效*/
    val buildPath = 2   //dev = 0 ,pre = 1 ,release = 2, test = 3 ,my_pc = 4,

    val DEFAULT_URL_C = "https://customer.yingwumeijia.com/"

    val DEFAULT_URL_E = "https://employee.yingwumeijia.com/"


    /*写死本机的环境*/
    val BASE_URL_DEFAULT = "https://testgate.yingwumeijia.com"
    val BASE_URL_H5_DEFAULT = "http://192.168.28.50:8090/"

    /*生产环境*/
    val BASE_URL_RELEASE = "https://gate.yingwumeijia.com/"
    val BASE_URL_H5_RELEASE = "https://mobile.yingwumeijia.com/appv/"

    /*预发布环境*/
    val BASE_URL_PRE = "https://gate.yingwumeijia.com/"
    val BASE_URL_H5_PRE = "https://premobile.yingwumeijia.com/appv/"

    /*开发环境*/
    val BASE_URL_DEV = "https://devgate.yingwumeijia.com"
    val BASE_URL_H5_DEV = "https://devmobile.yingwumeijia.com/appv/"

    /*测试环境*/
    val BASE_URL_TEST = "https://testgate.yingwumeijia.com"
    val BASE_URL_H5_TEST = "https://testmobile.yingwumeijia.com/appv/"


    fun severUrl(): String {
        Logger.d(BuildConfig.DEBUG)
        var url = ""
        when (buildPath) {
            0 -> return BASE_URL_DEV
            1 -> return BASE_URL_PRE
            2 -> return BASE_URL_RELEASE
            3 -> return BASE_URL_TEST
            4 -> return BASE_URL_DEFAULT
        }
        return SeverUrlManager.baseUrl()
    }

    fun baseH5Url(): String {
        when (buildPath) {
            0 -> return SeverUrlManager.baseWebUrl()
            1 -> return SeverUrlManager.baseWebUrl()
            2 -> return SeverUrlManager.baseWebUrl()
            3 -> return SeverUrlManager.baseWebUrl()
            4 -> return BASE_URL_H5_DEFAULT
        }
        return SeverUrlManager.baseWebUrl()
    }
}