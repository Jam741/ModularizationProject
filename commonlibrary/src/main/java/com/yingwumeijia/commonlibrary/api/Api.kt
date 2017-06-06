package com.yingwumeijia.commonlibrary.api

import com.yingwumeijia.commonlibrary.net.RetrofitUtil

/**
 * Created by jamisonline on 2017/6/2.
 */

class Api {


    companion object {


        fun getCommonService(): CommonApi {
            return RetrofitUtil.getInstance().getProxy(CommonApi::class.java)
        }

    }
}
