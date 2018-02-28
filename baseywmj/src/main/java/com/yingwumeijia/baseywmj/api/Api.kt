package com.yingwumeijia.baseywmj.api

import com.yingwumeijia.baseywmj.utils.net.RetrofitUtil

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
