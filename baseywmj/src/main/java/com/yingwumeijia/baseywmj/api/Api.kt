package com.yingwumeijia.baseywmj.api

import com.yingwumeijia.commonlibrary.api.CommonApi
import com.yingwumeijia.commonlibrary.net.RetrofitUtil

/**
 * Created by jamisonline on 2017/6/2.
 */

class Api {


    companion object {

        val service = service()

        private fun service(): Service {
            return RetrofitUtil.getInstance().getProxy(Service::class.java)
        }


    }
}
