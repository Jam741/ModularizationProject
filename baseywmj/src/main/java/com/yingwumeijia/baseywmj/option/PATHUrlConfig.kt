package com.yingwumeijia.baseywmj.option

import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.BuildConfig
import com.yingwumeijia.baseywmj.utils.net.SeverUrlManager

/**
 * Created by jamisonline on 2017/6/15.
 *
 * 网络环境配置
 */
object PATHUrlConfig {

    val severVesionControl = "v1/"

    /*编译环境配置，安全起见只对  debug包生效*/
    val buildPath = 0   //dev = 0 ,pre = 1 ,release = 2, test = 3 ,my_pc = 4,

    val DEFAULT_URL_C = "https://customer.yingwumeijia.com/"

    val DEFAULT_URL_E = "https://employee.yingwumeijia.com/"


    /*写死本机的环境*/
    val BASE_URL_DEFAULT_C = "https://devcustomer.yingwumeijia.com/" + severVesionControl
    val BASE_URL_DEFAULT_E = "https://devcustomer.yingwumeijia.com/" + severVesionControl

    val BASE_URL_H5_DEFAULT = "http://192.168.28.50:8090/"

    /*生产环境*/
    val BASE_URL_RELEASE_C = "https://customer.yingwumeijia.com/" + severVesionControl
    val BASE_URL_RELEASE_E = "https://customer.yingwumeijia.com/" + severVesionControl

    val BASE_URL_H5_RELEASE = "https://mobile.yingwumeijia.com/appv/"

    /*预发布环境*/
    val BASE_URL_PRE_C = "https://precustomer.yingwumeijia.com/" + severVesionControl
    val BASE_URL_PRE_E = "https://precustomer.yingwumeijia.com/" + severVesionControl

    val BASE_URL_H5_PRE = "https://premobile.yingwumeijia.com/appv/"

    /*开发环境*/
    val BASE_URL_DEV_C = "https://devcustomer.yingwumeijia.com/" + severVesionControl
    val BASE_URL_DEV_E = "https://devcustomer.yingwumeijia.com/" + severVesionControl

    val BASE_URL_H5_DEV = "https://devmobile.yingwumeijia.com/appv/"

    /*测试环境*/
    val BASE_URL_TEST_C = "https://testcustomer.yingwumeijia.com/" + severVesionControl
    val BASE_URL_TEST_E = "https://testcustomer.yingwumeijia.com/" + severVesionControl

    val BASE_URL_H5_TEST = "https://testmobile.yingwumeijia.com/appv/"


    fun severUrl(): String {

        var isAppc = AppTypeManager.isAppC()
        when (buildPath) {
            0 -> if (isAppc) return BASE_URL_DEV_C else BASE_URL_DEV_E
            1 -> if (isAppc) return BASE_URL_PRE_C else BASE_URL_PRE_E
            2 -> if (isAppc) return BASE_URL_RELEASE_C else BASE_URL_RELEASE_E
            3 -> if (isAppc) return BASE_URL_TEST_C else BASE_URL_TEST_E
            4 -> if (isAppc) return BASE_URL_DEFAULT_C else BASE_URL_DEFAULT_E
        }
        return ""
    }

    fun baseH5Url(): String {
        when (buildPath) {
            0 -> return BASE_URL_H5_DEV
            1 -> return BASE_URL_H5_PRE
            2 -> return BASE_URL_H5_RELEASE
            3 -> return BASE_URL_H5_TEST
            4 -> return BASE_URL_H5_DEFAULT
        }
        return SeverUrlManager.baseWebUrl()
    }
}